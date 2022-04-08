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
              id: 1,
              visitType: 'Emergency',
              visitStart: '2022-01-01',
              visitEnd: '2022-01-01',
              provider: 'Dr. Nick',
              careSite: 'Springfield Hospital'
            }
          ]
        };
      }
    });
    expect(wrapper.find('.visit-list').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType"]').text().includes('Emergency'));
    expect(wrapper.find('[data-field="visitStart"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitEnd"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="provider"]').text().includes('Dr. Nick'));
    expect(wrapper.find('[data-field="careSite"]').text().includes('Springfield Hospital'));
  });

  it('loads data', async () => {
    const visitData = [
      {
        id: 1,
        visitType: 'Emergency',
        visitStart: '2022-01-01',
        visitEnd: '2022-01-01',
        provider: 'Dr. Nick',
        careSite: 'Springfield Hospital'
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
    expect(wrapper.find('[data-field="visitType"]').text().includes('Emergency'));
    expect(wrapper.find('[data-field="visitStart"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitEnd"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="provider"]').text().includes('Dr. Nick'));
    expect(wrapper.find('[data-field="careSite"]').text().includes('Springfield Hospital'));
  });
});
