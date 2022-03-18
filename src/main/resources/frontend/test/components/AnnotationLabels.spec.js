import { mount } from '@vue/test-utils';
import AnnotationLabels from '@/components/AnnotationLabels';

describe('AnnotationLabels.vue', () => {
  it('renders', () => {
    const wrapper = mount(AnnotationLabels);
    expect(wrapper.find('.annotation-labels').exists()).toBe(true);
  });

});