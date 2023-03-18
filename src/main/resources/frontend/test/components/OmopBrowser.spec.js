import { flushPromises, mount } from '@vue/test-utils';

import OmopApi from '@/utils/omop-api';
import AnnotatorApi from '@/utils/annotator-api';
import OmopBrowser from '@/components/OmopBrowser';

import { mockFetchResponse } from '../helpers';
import exampleData from '../example-data';

describe('OmopBrowser.vue', () => {
  let mockApi;
  let mockAnnotatorApi;
  let defaultOptions;
  let visitApiMocks;

  beforeEach(() => {
    const { person, visits } = exampleData;

    mockApi = new OmopApi();
    mockAnnotatorApi = new AnnotatorApi();

    // pool entry requests
    spyOn(window, 'fetch').and.resolveTo(mockFetchResponse(exampleData.judgmentDto));

    // annotator state
    spyOn(mockAnnotatorApi, 'getPins').and.resolveTo([]);
    spyOn(mockAnnotatorApi, 'getDisplayConfig').and.resolveTo([]);

    // person requests
    spyOn(mockApi, 'getPerson').and.resolveTo(person);
    spyOn(mockApi, 'getVisitsForPerson').and.resolveTo(visits);

    // visit data requests
    const visitApiCalls = [
      'getConditionsForPersonAndVisit',
      'getObservationsForPersonAndVisit',
      'getProceduresForPersonAndVisit',
      'getMeasurementsForPersonAndVisit',
      'getNotesForPersonAndVisit',
      'getDrugsForPersonAndVisit'
    ];
    visitApiMocks = visitApiCalls.map(methodName => {
      return spyOn(mockApi, methodName).and.resolveTo([]);
    });

    defaultOptions = {
      props: { personId: person.id, poolEntryId: 12 },
      data: () => ({
        omopApi: mockApi,
        annotatorApi: mockAnnotatorApi
      })
    };
  });

  it('fetches person data on mount', async () => {
    const expectedId = defaultOptions.props.personId;

    mount(OmopBrowser, defaultOptions);
    await flushPromises();

    expect(mockApi.getPerson).toHaveBeenCalledWith(expectedId);
    expect(mockApi.getVisitsForPerson).toHaveBeenCalledWith(expectedId);
  });

  it('displays a loading indicator while loading person data', async () => {
    const wrapper = mount(OmopBrowser, defaultOptions);
    expect(wrapper.text()).toMatch('Loading');
  });

  it('fetches data when the personId prop changes', async () => {
    const wrapper = mount(OmopBrowser, defaultOptions);
    await flushPromises();

    expect(mockApi.getPerson).toHaveBeenCalledTimes(1);
    expect(mockApi.getVisitsForPerson).toHaveBeenCalledTimes(1);

    const newPersonId = 23456;
    await wrapper.setProps({ personId: newPersonId });
    await flushPromises();

    expect(mockApi.getPerson).toHaveBeenCalledTimes(2);
    expect(mockApi.getVisitsForPerson).toHaveBeenCalledTimes(2);

    expect(mockApi.getPerson.calls.argsFor(1)).toEqual([newPersonId]);
    expect(mockApi.getVisitsForPerson.calls.argsFor(1)).toEqual([newPersonId]);
  });

  it('displays placeholder text for visit data until a visit is selected', async () => {
    const wrapper = mount(OmopBrowser, defaultOptions);
    await flushPromises();

    expect(wrapper.text()).toMatch('Select a visit to see');
  });

  it('fetches data when a visit is selected', async () => {
    const wrapper = mount(OmopBrowser, defaultOptions);
    await flushPromises();

    visitApiMocks.forEach(mock => {
      expect(mock).not.toHaveBeenCalled();
    });

    // select the second visit
    await wrapper.findAll('tbody tr').at(1).trigger('click');
    await flushPromises();

    const expectedArgs = [exampleData.person.id, exampleData.visits[1].id];

    visitApiMocks.forEach(mock => {
      expect(mock).toHaveBeenCalledTimes(1);
      expect(mock).toHaveBeenCalledWith(...expectedArgs);
    });
  });

  it('displays a loading indicator while loading visit data', async () => {
    // return a promise that doesn't resolve to ensure the loading indicator is displayed
    const returnUnresolved = () => new Promise(() => {});
    visitApiMocks.forEach(mock => mock.and.callFake(returnUnresolved));

    const wrapper = mount(OmopBrowser, defaultOptions);
    await flushPromises();
    await wrapper.findAll('tbody tr').at(0).trigger('click');
    expect(wrapper.find('.tab-pane').text()).toMatch('Loading');
  });
});
