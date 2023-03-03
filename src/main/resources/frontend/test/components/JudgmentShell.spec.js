import { flushPromises, mount, shallowMount } from '@vue/test-utils';

import EntryJudgment from '@/components/EntryJudgment';
import JudgmentShell from '@/components/JudgmentShell';

import { judgmentDto, poolEntryJudgments } from '../example-data';
import { mockFetchResponse } from '../helpers';

const defaultProps = Object.freeze({
  poolId: 6,
  topicId: 2
});

describe('JudgmentShell.vue', () => {
  it('renders a list of pool entry judgments', () => {
    const wrapper = shallowMount(JudgmentShell, {
      props: defaultProps,
      data() {
        return {
          loading: false,
          entryJudgments: poolEntryJudgments,
          selectedEntryJudgment: poolEntryJudgments[0]
        };
      }
    });

    expect(wrapper.findAll('.list-group-item').length).toEqual(poolEntryJudgments.length);
    expect(wrapper.findAllComponents(EntryJudgment).length).toEqual(
      poolEntryJudgments.length
    );
  });

  it('loads pool entry judgments on mount', async () => {
    const expectedUrl = '/data/api/pool/6/topic/2/pool_entry_judgments';
    const mockResponse = mockFetchResponse(poolEntryJudgments);
    spyOn(window, 'fetch').and.callFake((url, _options) => {
      if (url.endsWith('pool_entry_judgments')) {
        // respond to the pool entry request
        return Promise.resolve(mockResponse);
      }

      // by default, return a response that doesn't resolve
      return new Promise(() => {});
    });

    const wrapper = mount(JudgmentShell, {
      props: defaultProps
    });
    await flushPromises();

    // umount the component to prevent the OMOP browser from making requests
    wrapper.unmount();

    expect(window.fetch).toHaveBeenCalled();
    expect(window.fetch.calls.argsFor(0)).toContain(expectedUrl);
  });

  it('selects the first pool entry by default', async () => {
    const wrapper = shallowMount(JudgmentShell, {
      props: defaultProps,
      data() {
        return {
          loading: false,
          entryJudgments: poolEntryJudgments,
          selectedEntryJudgment: null
        };
      }
    });

    await flushPromises();

    expect(wrapper.findAll('.entry-judgments a.active').length).toEqual(1);
    const listGroupItems = wrapper.findAll('.list-group-item');
    expect(listGroupItems.at(0).element.classList).toContain('active');
  });

  it('changes selected document on click', async () => {
    const wrapper = shallowMount(JudgmentShell, {
      props: defaultProps,
      data() {
        return {
          loading: false,
          entryJudgments: poolEntryJudgments,
          selectedEntryJudgment: null
        };
      }
    });

    await flushPromises();

    const listGroupItems = wrapper.findAll('.list-group-item');
    expect(listGroupItems.at(0).element.classList).toContain('active');

    await listGroupItems.at(1).trigger('click');
    expect(listGroupItems.at(1).element.classList).toContain('active');
    expect(listGroupItems.at(0).element.classList).not.toContain('active');
  });

  it('updates the state when a judgment is saved', () => {
    const expectedLabel = judgmentDto.annotationLabels[1].label;
    const wrapper = shallowMount(JudgmentShell, {
      props: defaultProps,
      data() {
        return {
          loading: false,
          entryJudgments: poolEntryJudgments,
          selectedEntryJudgment: poolEntryJudgments[0]
        };
      }
    });

    expect(wrapper.vm.entryJudgments[0].annotation).not.toEqual(expectedLabel);

    // Trigger the event handler directly to avoid mocking several requests.
    wrapper.vm.handleJudgment({
      ...judgmentDto,
      annotationLabelId: 8
    });

    expect(wrapper.vm.entryJudgments[0].annotation).toEqual(expectedLabel);
  });
});
