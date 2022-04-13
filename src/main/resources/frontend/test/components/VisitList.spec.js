import { mount } from '@vue/test-utils';
import VisitList from '@/components/VisitList';

const visits = [
  {
    id: 1,
    visitType: 'Emergency',
    visitStart: '2022-01-01',
    visitEnd: '2022-01-01',
    provider: 'Dr. Nick',
    careSite: 'Springfield Hospital'
  },
  {
    id: 2,
    visitType: 'Inpatient Visit',
    visitStart: '2022-02-02',
    visitEnd: '2022-02-03',
    provider: 'Dr. Hibbert',
    careSite: 'Shelbyville General Hospital'
  }
];

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
