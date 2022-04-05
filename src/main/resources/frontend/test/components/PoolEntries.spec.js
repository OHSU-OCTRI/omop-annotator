import { mount } from '@vue/test-utils';
import PoolEntries from '@/components/PoolEntries';
import flushPromises from 'flush-promises';

const entries_example = [
  {
    poolEntryId: 1,
    documentId: 123,
    sortOrder: 3,
    judgmentId: null,
    annotation: null
  },
  {
    poolEntryId: 2,
    documentId: 234,
    sortOrder: 1,
    judgmentId: null,
    annotation: null
  },
  {
    poolEntryId: 3,
    documentId: 345,
    sortOrder: 2,
    judgmentId: 1,
    annotation: 'Relevant'
  }
];

describe('PoolEntries.vue', () => {
  it('renders', () => {
    const wrapper = mount(PoolEntries, {
      props: { poolId: 1, topicId: 1 },
      data: function () {
        return {
          entries: [
            {
              poolEntryId: 1,
              documentId: 12345,
              sortOrder: 1,
              judgmentId: null,
              annotation: null
            }
          ],
          loading: false,
          showUnjudged: true
        };
      }
    });
    expect(wrapper.find('.pool-entries').exists()).toBe(true);
    expect(wrapper.find('[data-field="unjudged_poolEntryId"]').text().includes('1'));
    expect(wrapper.find('[data-field="unjudged_documentId"]').text().includes('12345'));
    expect(wrapper.find('[data-field="unjudged_judgment"]').find('a').exists()).toBe(
      true
    );
  });

  it('loads data', async () => {
    const poolEntryData = [
      {
        poolEntryId: 1,
        documentId: 12345,
        sortOrder: 1,
        judgmentId: null,
        annotation: null
      }
    ];

    const okResponse = new Response(JSON.stringify(poolEntryData), {
      status: 200,
      statusText: 'OK'
    });
    spyOn(window, 'fetch').and.resolveTo(okResponse);

    const wrapper = mount(PoolEntries, { props: { poolId: 1, topicId: 1 } });
    expect(window.fetch).toHaveBeenCalledWith(
      '/judge/pool/1/topic/1/pool_entry_judgments',
      {
        credentials: 'same-origin'
      }
    );
    expect(wrapper.find('.pool-entries').exists()).toBe(true);
    await flushPromises();
    expect(wrapper.find('.pool-entries').exists()).toBe(true);
    expect(wrapper.find('[data-field="unjudged_poolEntryId"]').text().includes('1'));
    expect(wrapper.find('[data-field="unjudged_documentId"]').text().includes('12345'));
    expect(wrapper.find('[data-field="unjudged_judgment"]').find('a').exists()).toBe(
      true
    );
  });

  it('renders only unjudged table by default', async () => {
    const wrapper = mount(PoolEntries, {
      props: { poolId: 1, topicId: 1 },
      data: function () {
        return {
          loading: false,
          showUnjudged: true,
          entries: entries_example
        };
      }
    });
    expect(isVisible(wrapper.find('#pool_1_topic_1_unjudged'))).toBe(true);
    expect(isVisible(wrapper.find('#pool_1_topic_1_judged'))).toBe(false);
  });

  it('toggles between judged and unjudged table', async () => {
    const wrapper = mount(PoolEntries, {
      props: { poolId: 1, topicId: 1 },
      data: function () {
        return {
          loading: false,
          showUnjudged: true,
          entries: entries_example
        };
      }
    });
    await wrapper.find('button').trigger('click');
    expect(isVisible(wrapper.find('#pool_1_topic_1_judged'))).toBe(true);
    expect(isVisible(wrapper.find('#pool_1_topic_1_unjudged'))).toBe(false);
    await wrapper.find('button').trigger('click');
    expect(isVisible(wrapper.find('#pool_1_topic_1_unjudged'))).toBe(true);
    expect(isVisible(wrapper.find('#pool_1_topic_1_judged'))).toBe(false);
  });

  it('displays the count of judged and unjudged entries', async () => {
    const wrapper = mount(PoolEntries, {
      props: { poolId: 1, topicId: 1 },
      data: function () {
        return {
          loading: false,
          showUnjudged: true,
          entries: entries_example
        };
      }
    });
    expect(wrapper.find('h3').text().includes('2'));
    expect(wrapper.find('button').text().includes('1'));
  });

  // TODO: The built-in isVisible function for Vue tests does not work for this component
  function isVisible(wrapper) {
    const styleAttr = wrapper.attributes('style');
    if (styleAttr === undefined) {
      return true;
    } else if (styleAttr.includes('display: none;')) {
      return false;
    }

    return true;
  }
});
