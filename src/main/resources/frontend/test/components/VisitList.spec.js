import { mount } from '@vue/test-utils';
import VisitList from '@/components/VisitList';

import { visits } from '../example-data';

describe('VisitList.vue', () => {
  it('renders', () => {
    const wrapper = mount(VisitList, {
      props: { visits: visits.slice(0, 1) }
    });
    expect(wrapper.find('.visit-list').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType"]').text().includes('Emergency')).toBe(
      true
    );
    expect(wrapper.find('[data-field="visitStart"]').text().includes('2022-01-01')).toBe(
      true
    );
    expect(wrapper.find('[data-field="visitEnd"]').text().includes('2022-01-01')).toBe(
      true
    );
    expect(wrapper.find('[data-field="provider"]').text().includes('Dr. Nick')).toBe(
      true
    );
    expect(
      wrapper.find('[data-field="careSite"]').text().includes('Springfield Hospital')
    ).toBe(true);
  });

  it('emits an event when a visit row is clicked', async () => {
    const wrapper = mount(VisitList, {
      props: { visits }
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
      props: { visits }
    });

    // nothing selected yet
    expect(wrapper.findAll('tr.table-active').length).toEqual(0);

    await wrapper.setProps({ selectedVisitId: visits[1].id });
    expect(wrapper.findAll('tr.table-active').length).toEqual(1);
  });
});
