<template>
  <div class="note-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped table-bordered table-sm w-100" ref="table">
        <thead>
          <tr>
            <th
              v-for="field in fieldsToShow"
              :key="field.field"
              :class="isNoteTextField(field.field) ? 'col-md-8' : null"
            >
              {{ field.display }}
            </th>
            <th v-if="showVisit">Visit Occurrence</th>
            <th class="d-none">Full Text</th>
          </tr>
          <tr v-if="indexesToFilter.length > 0" class="search-row">
            <th v-for="field in fieldsToShow" :key="field.field"></th>
            <th v-if="showVisit"></th>
            <th class="d-none"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(note, idx) in notes" :key="note.id">
            <td
              v-for="field in fieldsToShow"
              :key="field.field"
              :data-field="field.field"
              :class="isNoteTextField(field.field) ? 'col-md-8' : null"
              :title="isNoteTextField(field.field) ? 'Show/hide full note' : null"
              @click="isNoteTextField(field.field) ? toggleText(idx) : null"
              v-html="isNoteTextField(field.field) ? noteText(idx) : note[field.field]"
            ></td>
            <td v-if="showVisit" data-field="visitOccurrence">
              {{ note.visitOccurrence }}
            </td>
            <!-- Add a hidden column for the full text so it can be searched -->
            <td class="d-none" data-field="fullText">
              {{ note.text }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    notes: {
      type: Array,
      required: true
    },
    visitId: {
      type: Number,
      default: null
    },
    sortColumn: {
      type: Number,
      default: 0
    },
    sortOrder: {
      type: String,
      default: 'asc'
    },
    showHeader: {
      type: Boolean,
      default: true
    },
    showVisit: {
      type: Boolean,
      default: false
    },
    previewSize: {
      type: Number,
      default: 200
    }
  },
  data() {
    return {
      showFullText: [],
      configuration: [
        { field: 'id', display: 'Id', filter: false, show: true },
        { field: 'date', display: 'Date/Time', filter: false, show: true },
        { field: 'type', display: 'Type', filter: true, show: true },
        { field: 'title', display: 'Title', filter: true, show: true },
        { field: 'text', display: 'Text', filter: false, show: true }
      ]
    };
  },
  mounted() {
    this.showFullText = new Array(this.notes.length);
    for (let i = 0; i < this.notes.length; i++) {
      this.showFullText[i] = false;
    }
    this.$nextTick(this.drawDataTable);
  },
  computed: {
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Notes${filter}`;
    },
    fieldsToShow() {
      return this.configuration.filter(f => f.show === true);
    },
    indexesToFilter() {
      const b = this.fieldsToShow
        .map((item, index) => ({ index, ...item }))
        .filter(f => f.filter === true)
        .map(f => f.index);
      return b;
    }
  },
  methods: {
    drawDataTable() {
      // Format with the datatables library if it is available.
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        if (this.dataTable) {
          this.dataTable.clear().destroy();
        }

        const indexesToFilter = this.indexesToFilter;
        this.dataTable = $(this.$refs.table).DataTable({
          order: [[this.sortColumn, this.sortOrder]],
          paging: true,
          searching: true,
          info: true,
          orderCellsTop: true,
          initComplete: function () {
            if (indexesToFilter.length > 0) {
              this.api()
                .columns(indexesToFilter)
                .every(function () {
                  const column = this;
                  let select = $('<select><option value=""></option></select>')
                    .appendTo($('.note-list .search-row th').eq(column.index()).empty())
                    .on('change', function () {
                      const val = $.fn.dataTable.util.escapeRegex($(this).val());

                      column.search(val ? '^' + val + '$' : '', true, false).draw();
                    });

                  column
                    .data()
                    .unique()
                    .sort()
                    .each(function (d, j) {
                      select.append('<option value="' + d + '">' + d + '</option>');
                    });
                });
            }
          }
        });
      }
    },
    noteText(idx) {
      if (this.showFullText[idx] === true) {
        return this.expand(this.notes[idx].text);
      }
      return this.preview(this.notes[idx].text);
    },
    preview(text) {
      let sub = text.substring(0, this.previewSize);
      if (sub.length === this.previewSize) {
        sub = sub.concat('... <i class="fas fa-angle-double-right text-primary"></i>');
      }
      return sub;
    },
    expand(text) {
      if (text.length > this.previewSize) {
        text = text.concat(' <i class="fas fa-angle-double-left text-primary"></i>');
      }
      return text;
    },
    toggleText(idx) {
      this.showFullText[idx] = !this.showFullText[idx];
    },
    isNoteTextField(field) {
      if (field === 'text') return true;
      return false;
    }
  },
  watch: {
    procedures() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
