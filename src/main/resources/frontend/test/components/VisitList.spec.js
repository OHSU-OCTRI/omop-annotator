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
    expect(wrapper.find('[data-field="visitType"]').text().includes('Emergency'));
    expect(wrapper.find('[data-field="visitStart"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitEnd"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitSource"]').text().includes('Visit Source'));
    expect(
      wrapper.find('[data-field="admittingSource"]').text().includes('Admitting Source')
    );
    expect(wrapper.find('[data-field="dischargedTo"]').text().includes('Discharged To'));
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
    await flushPromises();
    expect(window.fetch).toHaveBeenCalledWith('/data/person/summary/123456/visits', {
      credentials: 'same-origin'
    });
    expect(wrapper.find('.visit-list').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType"]').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType"]').text().includes('Emergency'));
    expect(wrapper.find('[data-field="visitStart"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitEnd"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitSource"]').text().includes('Visit Source'));
    expect(
      wrapper.find('[data-field="admittingSource"]').text().includes('Admitting Source')
    );
    expect(wrapper.find('[data-field="dischargedTo"]').text().includes('Discharged To'));
  });
});
