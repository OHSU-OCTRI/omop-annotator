<template>
  <div class="note-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table
        class="table table-striped table-bordered table-sm w-100"
        ref="table"
        aria-label="Note List"
      >
        <thead>
          <tr>
            <th
              v-for="field in fieldsToShow"
              :key="field.fieldName"
              :class="isNoteTextField(field.fieldName) ? 'col-md-8' : null"
              scope="col"
            >
              {{ field.columnDisplay }}
            </th>
            <th v-if="showVisit" scope="col">Visit Occurrence</th>
            <th class="d-none" scope="col">Full Text</th>
          </tr>
          <tr v-if="indexesToFilter.length > 0" class="search-row">
            <th v-for="field in fieldsToShow" :key="field.fieldName" scope="col"></th>
            <th v-if="showVisit" scope="col"></th>
            <th class="d-none" scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(note, idx) in notes" :key="note.id">
            <td
              v-for="field in fieldsToShow"
              :key="field.fieldName"
              :data-field="field.fieldName"
              :class="isNoteTextField(field.fieldName) ? 'col-md-8' : null"
              :title="isNoteTextField(field.fieldName) ? 'Show/hide full note' : null"
              @click="isNoteTextField(field.fieldName) ? toggleText(idx) : null"
              v-html="
                isNoteTextField(field.fieldName) ? noteText(idx) : note[field.fieldName]
              "
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
    configuration: {
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
      showFullText: []
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
      return this.configuration.filter(f => f.visible === true);
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
      return field === 'text';
    }
  },
  watch: {
    procedures() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
