import { mount } from '@vue/test-utils';
import PlaceholderMessage from '@/components/PlaceholderMessage';

describe('PlaceholderMessage.vue', () => {
  it('renders the message prop', () => {
    const expectedText = 'This is a test';
    const wrapper = mount(PlaceholderMessage, {
      props: {
        message: expectedText
      }
    });
    expect(wrapper.text()).toMatch(expectedText);
  });
});
