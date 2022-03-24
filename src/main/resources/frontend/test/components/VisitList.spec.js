import { mount } from '@vue/test-utils';
import VisitList from '@/components/VisitList';
import flushPromises from 'flush-promises';

describe('VisitList.vue', () => {
  it('renders', () => {
    const wrapper = mount(VisitList, {
      props: { personId: 12345 },
      data: function () {
        return {
          loading: false,
          visits: [
            {
              visitType: { name: 'Emergency' },
              visitStart: '2022-01-01',
              visitEnd: '2022-01-01',
              visitSource: 'Visit Source',
              admittingSource: 'Admitting Source',
              dischargedTo: 'Discharged To'
            }
          ]
        };
      }
    });
    expect(wrapper.find('.visit-list').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Emergency'));
    expect(wrapper.find('[data-field="visitStart0"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Visit Source'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Admitting Source'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Discharged To'));
    expect(wrapper.find('[data-field="visitType1"]').exists()).toBe(false);
  });

  it('loads data', async () => {
    const visitData = [
      {
        visitType: { name: 'Emergency' },
        visitStart: '2022-01-01',
        visitEnd: '2022-01-01',
        visitSource: 'Visit Source',
        admittingSource: 'Admitting Source',
        dischargedTo: 'Discharged To'
      }
    ];

    const okResponse = new Response(JSON.stringify(visitData), {
      status: 200,
      statusText: 'OK'
    });
    spyOn(window, 'fetch').and.resolveTo(okResponse);

    const wrapper = mount(VisitList, { props: { personId: 123456 } });
    expect(window.fetch).toHaveBeenCalledWith('/data/person/summary/123456/visits', {
      credentials: 'same-origin'
    });
    expect(wrapper.find('.visit-list').exists()).toBe(true);
    await flushPromises();
    expect(wrapper.find('[data-field="visitType0"]').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Emergency'));
    expect(wrapper.find('[data-field="visitStart0"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Visit Source'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Admitting Source'));
    expect(wrapper.find('[data-field="visitType0"]').text().includes('Discharged To'));
    expect(wrapper.find('[data-field="visitType1"]').exists()).toBe(false);
  });
});
