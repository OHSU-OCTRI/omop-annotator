import { mount } from '@vue/test-utils';
import VisitList from '@/components/VisitList';

import { visits } from '../example-data';

describe('VisitList.vue', () => {
  it('renders', () => {
    const wrapper = mount(VisitList, {
      props: { visits: visits.slice(0, 1) }
    });
    expect(wrapper.find('.visit-list').exists()).toBe(true);
    expect(wrapper.find('[data-field="visitType"]').text().includes('Emergency'));
    expect(wrapper.find('[data-field="visitStart"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="visitEnd"]').text().includes('2022-01-01'));
    expect(wrapper.find('[data-field="provider"]').text().includes('Dr. Nick'));
    expect(
      wrapper.find('[data-field="careSite"]').text().includes('Springfield Hospital')
    );
  });
});
