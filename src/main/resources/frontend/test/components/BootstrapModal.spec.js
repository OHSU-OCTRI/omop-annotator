import { shallowMount } from '@vue/test-utils';
import BootstrapModal from '@/components/BootstrapModal';

describe('BootstrapModal.vue', () => {
  const defaultProps = {
    modalId: 'testId',
    modalTitle: 'Test title'
  };

  it('renders Bootstrap modal markup', () => {
    const wrapper = shallowMount(BootstrapModal, {
      props: defaultProps
    });

    expect(wrapper.find('.modal').exists()).toBe(true);
    expect(wrapper.find('.modal-header').exists()).toBe(true);
    expect(wrapper.find('.modal-body').exists()).toBe(true);
    expect(wrapper.find('.modal-footer').exists()).toBe(true);
  });

  it('sets the ID to the modalId prop', () => {
    const expectedId = 'banana';
    const wrapper = shallowMount(BootstrapModal, {
      props: {
        ...defaultProps,
        modalId: expectedId
      }
    });
    expect(wrapper.attributes('id')).toEqual(expectedId);
  });

  it('sets the size class using the modalSize prop', async () => {
    const validSizes = ['sm', 'lg', 'xl'];

    const wrapper = shallowMount(BootstrapModal, {
      props: defaultProps
    });

    const dialogDiv = wrapper.find('div.modal-dialog');

    for (let size of validSizes) {
      expect(dialogDiv.element.classList).not.toContain(`modal-${size}`);
    }

    for (let size of validSizes) {
      await wrapper.setProps({ modalSize: size });
      expect(dialogDiv.element.classList).toContain(`modal-${size}`);
    }
  });

  it('sets `data-bs-backdrop` attribute based on showOverlay prop', async () => {
    const wrapper = shallowMount(BootstrapModal, {
      props: defaultProps
    });

    expect(wrapper.find('.modal').element.dataset.bsBackdrop).toEqual('false');

    await wrapper.setProps({ showOverlay: true });
    expect(wrapper.find('.modal').element.dataset.bsBackdrop).toEqual('true');
  });

  it('displays the modalTitle prop in the title', () => {
    const expectedTitle = 'Banana!';
    const wrapper = shallowMount(BootstrapModal, {
      props: {
        ...defaultProps,
        modalTitle: expectedTitle
      }
    });
    expect(wrapper.find('h5').text()).toEqual(expectedTitle);
  });

  it('has a slot for header content (overrides title)', () => {
    const expectedContent = 'Header content from slot';
    const wrapper = shallowMount(BootstrapModal, {
      props: defaultProps,
      slots: {
        headerContent: `<h5>${expectedContent}</h5>`
      }
    });
    expect(wrapper.find('h5').text()).toEqual(expectedContent);
    expect(wrapper.text()).not.toContain(defaultProps.modalTitle);
  });

  it('has a slot for the modal content', () => {
    const expectedContent = 'Slot content!';
    const wrapper = shallowMount(BootstrapModal, {
      props: defaultProps,
      slots: {
        default: `<p>${expectedContent}</p>`
      }
    });
    expect(wrapper.findAll('.modal-body p').length).toBeGreaterThan(0);
    expect(wrapper.find('.modal-body p').text()).toContain(expectedContent);
  });

  it('has a slot for footer content (overrides close button)', () => {
    const expectedContent = 'Footer content';
    const wrapper = shallowMount(BootstrapModal, {
      props: defaultProps,
      slots: {
        footerContent: `<a href="#">${expectedContent}</a>`
      }
    });

    const footer = wrapper.find('.modal-footer');
    expect(footer.findAll('button').length)
      .withContext('default button is not present')
      .toEqual(0);
    expect(footer.findAll('a').length).toEqual(1);
    expect(footer.text()).toContain(expectedContent);
  });

  it('prevents closing the modal when allowClose prop is false', () => {
    const wrapper = shallowMount(BootstrapModal, {
      props: {
        ...defaultProps,
        allowClose: false
      }
    });

    expect(wrapper.findAll('button').length)
      .withContext('close buttons are not present')
      .toEqual(0);
    expect(wrapper.find('.modal-footer').exists()).toBeFalse('footer is hidden');

    const modalDataProps = wrapper.find('.modal').element.dataset;
    expect(modalDataProps.bsBackdrop).toEqual('static');
    expect(modalDataProps.bsKeyboard).toEqual('false');
  });

  it('overrides showOverlay when allowClose is false', () => {
    const wrapper = shallowMount(BootstrapModal, {
      props: {
        ...defaultProps,
        allowClose: false
      }
    });
    expect(wrapper.find('.modal').element.dataset.bsBackdrop).toEqual('static');
  });
});
