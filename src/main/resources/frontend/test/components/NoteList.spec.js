import { mount } from '@vue/test-utils';
import NoteList from '@/components/NoteList';

import { isVisible } from '../helpers';
import { notes, notesConfiguration } from '../example-data';

describe('NoteList.vue', () => {
  it('renders', () => {
    const wrapper = mount(NoteList, {
      props: { notes: notes.slice(0, 1), configuration: notesConfiguration, visitId: 1 }
    });
    expect(wrapper.find('.note-list').exists()).toBe(true);
    expect(wrapper.find('[data-field="date"]').text().includes('2022-01-01')).toBe(true);
    expect(wrapper.find('[data-field="type"]').text().includes('EHR')).toBe(true);
    expect(wrapper.find('[data-field="title"]').text().includes('Long Note')).toBe(true);
  });

  it('truncates text when the note exceeds preview length', () => {
    const wrapper = mount(NoteList, {
      props: {
        notes: notes.slice(0, 1),
        configuration: notesConfiguration,
        visitId: 1,
        previewSize: 50
      }
    });
    expect(wrapper.find('[data-field="text"]').text().length === 53).toBe(true);
    expect(wrapper.find('[data-field="text"]').text().includes('...')).toBe(true);
    expect(!wrapper.find('[data-field="text"]').text().includes('Spoon')).toBe(true);
  });

  it('expands text when the note is clicked', async () => {
    const wrapper = mount(NoteList, {
      props: {
        notes: notes,
        configuration: notesConfiguration,
        visitId: 1,
        previewSize: 50
      }
    });
    const noteCells = wrapper.findAll('[data-field="text"]');
    expect(noteCells.length).toEqual(notes.length);

    await noteCells.at(0).trigger('click');
    expect(wrapper.find('[data-field="text"]').text().length > 53).toBe(true);
    expect(!wrapper.find('[data-field="text"]').text().includes('...')).toBe(true);
    expect(wrapper.find('[data-field="text"]').text().includes('Spoon')).toBe(true);
  });

  it('hides the full text so it is still searchable', () => {
    const wrapper = mount(NoteList, {
      props: {
        notes: notes.slice(0, 1),
        configuration: notesConfiguration,
        visitId: 1,
        previewSize: 50
      }
    });
    expect(isVisible(wrapper.find('[data-field="fullText"]'))).toBe(false);
    expect(wrapper.find('[data-field="fullText"]').text().length > 53).toBe(true);
  });
});
