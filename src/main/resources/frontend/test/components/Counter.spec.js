import { mount } from '@vue/test-utils';
import Counter from '@/components/Counter';

describe('Counter.vue', () => {
  it('renders', () => {
    const wrapper = mount(Counter);
    expect(wrapper.find('button').exists()).toBe(true);
  });

  it('increments the counter when the button is clicked', async () => {
    const wrapper = mount(Counter);
    expect(wrapper.find('button').element.innerText).toEqual('Count is: 0');

    await wrapper.find('button').trigger('click');
    expect(wrapper.find('button').element.innerText).toEqual('Count is: 1');
  });
});