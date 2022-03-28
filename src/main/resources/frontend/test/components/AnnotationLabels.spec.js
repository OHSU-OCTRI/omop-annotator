import { mount } from '@vue/test-utils';
import AnnotationLabels from '@/components/AnnotationLabels';

describe('AnnotationLabels.vue', () => {
  it('renders', () => {
    const wrapper = mount(AnnotationLabels);
    expect(wrapper.find('.annotation-labels').exists()).toBe(true);
  });

  it('creates a table row when Add is clicked', async () => {
    const wrapper = mount(AnnotationLabels);
    expect(wrapper.find('input[name="annotationLabels[0].displayOrder"]').exists()).toBe(
      false
    );
    await wrapper.find('.new-entity').trigger('click');
    expect(wrapper.find('input[name="annotationLabels[0].displayOrder"').exists()).toBe(
      true
    );
  });

  it('deletes a table row when Delete is clicked', async () => {
    const wrapper = mount(AnnotationLabels);
    await wrapper.find('.new-entity').trigger('click');
    wrapper.find('input[name="annotationLabels[0].displayLabel"').setValue('Label 1');
    await wrapper.find('.new-entity').trigger('click');
    wrapper.find('input[name="annotationLabels[1].displayLabel"').setValue('Label 2');
    expect(wrapper.vm.labels.length).toEqual(2);
    await wrapper.find('[name=delete0').trigger('click');
    expect(wrapper.vm.labels.length).toEqual(1);
    expect(wrapper.vm.labels[0].displayLabel).toEqual('Label 2');
  });

  it('validates displayOrder is unique', async () => {
    const wrapper = mount(AnnotationLabels);
    await wrapper.find('.new-entity').trigger('click');
    let input1 = wrapper.find('input[name="annotationLabels[0].displayOrder"');
    input1.setValue('1');
    expect(input1.classes('is-invalid')).toBe(false);
    await wrapper.find('.new-entity').trigger('click');
    let input2 = wrapper.find('input[name="annotationLabels[1].displayOrder"');
    input2.setValue('1');
    expect(input2.classes('is-invalid')).toBe(true);
    input2.setValue('2');
    expect(input2.classes('is-invalid')).toBe(false);
  });

  it('validates displayLabel is unique', async () => {
    const wrapper = mount(AnnotationLabels);
    await wrapper.find('.new-entity').trigger('click');
    let input1 = wrapper.find('input[name="annotationLabels[0].displayLabel"');
    input1.setValue('1');
    expect(input1.classes('is-invalid')).toBe(false);
    await wrapper.find('.new-entity').trigger('click');
    let input2 = wrapper.find('input[name="annotationLabels[1].displayLabel"');
    input2.setValue('1');
    expect(input2.classes('is-invalid')).toBe(true);
    input2.setValue('2');
    expect(input2.classes('is-invalid')).toBe(false);
  });

  it('validates outputLabel is unique', async () => {
    const wrapper = mount(AnnotationLabels);
    await wrapper.find('.new-entity').trigger('click');
    let input1 = wrapper.find('input[name="annotationLabels[0].outputLabel"');
    input1.setValue('1');
    expect(input1.classes('is-invalid')).toBe(false);
    await wrapper.find('.new-entity').trigger('click');
    let input2 = wrapper.find('input[name="annotationLabels[1].outputLabel"');
    input2.setValue('1');
    expect(input2.classes('is-invalid')).toBe(true);
    input2.setValue('2');
    expect(input2.classes('is-invalid')).toBe(false);
  });

  it('validates accentColor is unique', async () => {
    const wrapper = mount(AnnotationLabels);
    await wrapper.find('.new-entity').trigger('click');
    let input1 = wrapper.find('input[name="annotationLabels[0].accentColor"');
    input1.setValue('#ffffff');
    expect(input1.classes('is-invalid')).toBe(false);
    await wrapper.find('.new-entity').trigger('click');
    let input2 = wrapper.find('input[name="annotationLabels[1].accentColor"');
    input2.setValue('#ffffff');
    expect(input2.classes('is-invalid')).toBe(true);
    input2.setValue('#000000');
    expect(input2.classes('is-invalid')).toBe(false);
  });

});
