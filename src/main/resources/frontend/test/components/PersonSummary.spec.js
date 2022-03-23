import { mount } from '@vue/test-utils';
import PersonSummary from '@/components/PersonSummary';

describe('PersonSummary.vue', () => {
	it('renders summary fields', () => {
		const wrapper = mount(PersonSummary, { "props": { "personId": 12345 }, "data": function() {
			return {
				"person": {
					"id": 12345,
					"monthOfBirth": 1,
					"yearOfBirth": 1990,
					"age": 22,
					"gender": "Male",
					"race": "White",
					"ethnicity": "Non-Hispanic"
				}
			}
		}});
		expect(wrapper.find('.person-summary').exists()).toBe(true);
		expect(wrapper.find('[data-field="id"]').text().includes("12345"));
		expect(wrapper.find('[data-field="dob"]').text().includes("1-1990"));
		expect(wrapper.find('[data-field="age"]').text().includes("22"));
		expect(wrapper.find('[data-field="gender"]').text().includes("Male"));
		expect(wrapper.find('[data-field="race"]').text().includes("White"));
		expect(wrapper.find('[data-field="ethnicity"]').text().includes("Non-Hispanic"));
	});
});

