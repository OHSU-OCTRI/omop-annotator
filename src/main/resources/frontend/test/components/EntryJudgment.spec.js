import { mount } from '@vue/test-utils';

import EntryJudgment from '@/components/EntryJudgment';

const undjudgedEntry = {
  judgmentId: null,
  poolEntryId: 12,
  sortOrder: 1,
  documentId: 42,
  annotation: null
};

const judgedEntry = {
  judgmentId: 13,
  poolEntryId: 12,
  sortOrder: 1,
  documentId: 42,
  annotation: 'Relevant'
};

describe('EntryJudgment.vue', () => {
  it('displays the document ID', () => {
    const expectedText = undjudgedEntry.documentId.toString();
    const wrapper = mount(EntryJudgment, {
      props: {
        entryJudgment: undjudgedEntry
      }
    });
    expect(wrapper.text().trim()).toEqual(expectedText);
  });

  it('shows the judgment when showJudgment prop is true', () => {
    const judgmentText = judgedEntry.annotation;
    const wrapper = mount(EntryJudgment, {
      props: {
        entryJudgment: judgedEntry
      }
    });
    expect(wrapper.text()).toContain(judgedEntry.documentId);
    expect(wrapper.text()).toContain(judgmentText);
  });

  it('hides the judgment when showJudgment prop is false', () => {
    const judgmentText = judgedEntry.annotation;
    const wrapper = mount(EntryJudgment, {
      props: {
        entryJudgment: judgedEntry,
        showJudgment: false
      }
    });
    expect(wrapper.text()).toContain(judgedEntry.documentId);
    expect(wrapper.text()).not.toContain(judgmentText);
  });

  it('shows an icon when showJudgment prop is false', () => {
    const wrapper = mount(EntryJudgment, {
      props: {
        entryJudgment: judgedEntry,
        showJudgment: false
      }
    });
    expect(wrapper.find('.fa-check-circle').exists()).toBe(true);
  });

  it('adapts the icon classes when item is selected', async () => {
    const wrapper = mount(EntryJudgment, {
      props: {
        entryJudgment: judgedEntry,
        showJudgment: false
      }
    });

    const iconWrapper = wrapper.find('.fa-check-circle');
    expect(iconWrapper.exists()).toBe(true);

    // classList seems to be mutated in place, so capture the original values
    // to prevent comparing the object to itself
    const iconClasses = [...iconWrapper.element.classList];

    await wrapper.setProps({ selected: true });

    const updatedWrapper = wrapper.find('.fa-check-circle');
    const updatedClasses = [...updatedWrapper.element.classList];
    expect(updatedClasses).not.toEqual(iconClasses);
  });
});
