import { mount } from '@vue/test-utils';
import PersonSummary from '@/components/PersonSummary';

import { person } from '../example-data';

describe('PersonSummary.vue', () => {
  it('renders summary fields', () => {
    const wrapper = mount(PersonSummary, {
      props: { person }
    });

    expect(wrapper.find('.person-summary').exists()).toBe(true);
    expect(wrapper.find('[data-field="id"]').text().includes('12345'));
    expect(wrapper.find('[data-field="dob"]').text().includes('1-1990'));
    expect(wrapper.find('[data-field="age"]').text().includes('22'));
    expect(wrapper.find('[data-field="gender"]').text().includes('Male'));
    expect(wrapper.find('[data-field="race"]').text().includes('White'));
    expect(wrapper.find('[data-field="ethnicity"]').text().includes('Non-Hispanic'));
  });
});
