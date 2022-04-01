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
          ]
        };
      }
    });
    expect(wrapper.find('.pool-entries').exists()).toBe(true);
    expect(wrapper.find('[data-field="poolEntryId0"]').text().includes('1'));
    expect(wrapper.find('[data-field="documentId0"]').text().includes('12345'));
    expect(wrapper.find('[data-field="judgment0"]').find('a').exists()).toBe(true);
    expect(wrapper.find('[data-field="poolEntryId1"]').exists()).toBe(false);
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
    expect(wrapper.find('[data-field="poolEntryId0"]').text().includes('1'));
    expect(wrapper.find('[data-field="documentId0"]').text().includes('12345'));
    expect(wrapper.find('[data-field="judgment0"]').find('a').exists()).toBe(true);
    expect(wrapper.find('[data-field="poolEntryId1"]').exists()).toBe(false);
  });

  it('renders only unjudged entries by default', async () => {
    const wrapper = mount(PoolEntries, {
      props: { poolId: 1, topicId: 1 },
      data: function () {
        return {
          entries: entries_example
        };
      }
    });
    expect(wrapper.findAll('tr')).toHaveSize(3);
    expect(wrapper.find('h3').text().includes('Unjudged'));
    expect(wrapper.find('button').text().includes('Unjudged')).toBe(false);
  });

  it('toggles between judged and unjudged entries', async () => {
    const wrapper = mount(PoolEntries, {
      props: { poolId: 1, topicId: 1 },
      data: function () {
        return {
          entries: entries_example
        };
      }
    });
    await wrapper.find('button').trigger('click');
    expect(wrapper.findAll('tr')).toHaveSize(2);
    expect(wrapper.find('h3').text().includes('Unjudged')).toBe(false);
    expect(wrapper.find('button').text().includes('Unjudged'));
    await wrapper.find('button').trigger('click');
    expect(wrapper.findAll('tr')).toHaveSize(3);
    expect(wrapper.find('h3').text().includes('Unjudged'));
    expect(wrapper.find('button').text().includes('Unjudged')).toBe(false);
  });

  it('displays the count of judged and unjudged entries', async () => {
    const wrapper = mount(PoolEntries, {
      props: { poolId: 1, topicId: 1 },
      data: function () {
        return {
          entries: entries_example
        };
      }
    });
    expect(wrapper.find('h3').text().includes('2'));
    expect(wrapper.find('button').text().includes('1'));
  });
});
