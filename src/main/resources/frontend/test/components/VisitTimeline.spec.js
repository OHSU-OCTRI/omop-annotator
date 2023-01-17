import { mount } from '@vue/test-utils';
import VisitTimeline from '@/components/VisitTimeline';
import { visits } from '../example-data';

describe('VisitTimeline.vue', () => {
  it('renders', () => {
    const wrapper = mount(VisitTimeline, {
      props: { visits: visits }
    });
    const circles = wrapper.findAll('.timeline-circle').map(e => e.wrapperElement);
    expect(circles.length).toEqual(visits.length);

    // Sizes should be the same
    const sizes = circles.map(circle => circle.getAttribute('r'));
    const firstSize = sizes[0];
    expect(sizes.every(size => size === firstSize)).toBe(true);

    expect(wrapper.findAll('.timeline-circle').length).toEqual(visits.length);
    expect(wrapper.findAll('.timeline-label').length).toEqual(2);
  });

  it('groups visits by date', () => {
    const sampleVisits = [
      Object.freeze({
        id: 1,
        visitType: 'Emergency',
        visitStartIsoDate: '2022-01-01'
      }),
      Object.freeze({
        id: 2,
        visitType: 'Inpatient Visit',
        visitStartIsoDate: '2022-02-02'
      }),
      Object.freeze({
        id: 3,
        visitType: 'Pharmacy',
        visitStartIsoDate: '2022-02-02'
      })
    ];
    const wrapper = mount(VisitTimeline, {
      props: { visits: sampleVisits }
    });
    const circles = wrapper.findAll('.timeline-circle').map(e => e.wrapperElement);
    expect(circles.length).toEqual(2);
    // Sizes should be different
    expect(circles[0].getAttribute('r')).not.toEqual(circles[1].getAttribute('r'));
    expect(wrapper.findAll('.timeline-label').length).toEqual(2);
  });

  it('excludes visits without a visitStartIsoDate', () => {
    const sampleVisits = [
      Object.freeze({
        id: 1,
        visitType: 'Emergency',
        visitStartIsoDate: null
      }),
      Object.freeze({
        id: 2,
        visitType: 'Inpatient Visit',
        visitStartIsoDate: '2022-02-02'
      })
    ];
    const wrapper = mount(VisitTimeline, {
      props: { visits: sampleVisits }
    });
    expect(wrapper.findAll('.timeline-circle').length).toEqual(1);
  });

  it('emits an event when a visit date is clicked', async () => {
    const sampleVisits = [
      Object.freeze({
        id: 1,
        visitType: 'Emergency',
        visitStartIsoDate: '2022-01-01'
      }),
      Object.freeze({
        id: 2,
        visitType: 'Inpatient Visit',
        visitStartIsoDate: '2022-02-02'
      })
    ];
    const wrapper = mount(VisitTimeline, {
      props: { visits: sampleVisits }
    });

    const circles = wrapper.findAll('.timeline-circle');
    await circles.at(1).trigger('click');
    expect(wrapper.emitted('date-selected').length).toEqual(1);
    expect(wrapper.emitted('date-selected')[0][0]).toEqual('2022-02-02');
  });
});
