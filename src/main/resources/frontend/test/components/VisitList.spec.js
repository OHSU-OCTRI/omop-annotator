import { mount } from '@vue/test-utils';
import VisitList from '@/components/VisitList';

import { visits, visitsConfiguration } from '../example-data';

describe('VisitList.vue', () => {
  it('renders', () => {
    const wrapper = mount(VisitList, {
      props: {
        poolEntryId: 1,
        visits: visits.slice(0, 1),
        pins: [],
        configuration: visitsConfiguration,
        personId: 1234
      }
    });
    expect(wrapper.find('.visit-list').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType"]').text().includes('Emergency')).toBe(
      true
    );
    // This is configured to not be visible so should not exist
    expect(wrapper.find('[data-field="careTeamName"]').exists()).toBe(false);
  });

  it('emits an event when a visit row is clicked', async () => {
    const wrapper = mount(VisitList, {
      props: {
        poolEntryId: 1,
        visits: visits,
        pins: [],
        configuration: visitsConfiguration,
        personId: 1234
      }
    });

    const visitRows = wrapper.findAll('tbody tr');
    expect(visitRows.length).toEqual(visits.length);

    await visitRows.at(1).trigger('click');
    expect(wrapper.emitted('visit-selected').length).toEqual(1);
    expect(wrapper.emitted('visit-selected')[0]).toEqual([visits[1].id]);
  });

  it('adds a class to the selected visit row', async () => {
    const wrapper = mount(VisitList, {
      // selectedVisitId is null by default
      props: {
        poolEntryId: 1,
        visits: visits,
        pins: [],
        configuration: visitsConfiguration,
        personId: 1234
      }
    });

    // nothing selected yet
    expect(wrapper.findAll('tr.table-active').length).toEqual(0);

    await wrapper.setProps({ selectedVisitId: visits[1].id });
    expect(wrapper.findAll('tr.table-active').length).toEqual(1);
  });
});
