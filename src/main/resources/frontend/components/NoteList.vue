<template>
  <div class="note-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table
        class="table table-striped table-bordered table-sm"
        ref="table"
        style="width: 100%"
      >
        <thead>
          <tr>
            <th class="no-sort"></th>
            <th>Id</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Title</th>
            <th class="col-md-8">Text Preview</th>
            <th v-if="showVisit">Visit Occurrence</th>
            <th class="no-sort d-none">Text</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(note, idx) in notes" :key="note.id">
            <td
              class="text-center details-control"
              @click="toggleDetails"
              title="Show/hide full note"
            ></td>
            <td data-field="id">
              {{ note.id }}
            </td>
            <td data-field="noteDatetime">
              {{ note.date }}
            </td>
            <td data-field="noteType">
              {{ note.type }}
            </td>
            <td data-field="title">
              {{ note.title }}
            </td>
            <td
              class="col-md-8"
              data-field="text"
              @click="toggleText(idx)"
              title="Show/hide full note"
              v-html="noteText(idx)"
            ></td>
            <td v-if="showVisit" data-field="visitOccurrence">
              {{ note.visitOccurrence }}
            </td>
            <!-- Add a hidden column for the full text so it can be searched -->
            <td class="d-none">
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
      default: 2
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
    console.log(this.showFullText);
    this.$nextTick(this.drawDataTable);
  },
  computed: {
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Notes${filter}`;
    }
  },
  methods: {
    drawDataTable() {
      // Format with the datatables library if it is available.
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        if (this.dataTable) {
          this.dataTable.clear().destroy();
        }

        this.dataTable = $(this.$refs.table).DataTable({
          order: [[this.sortColumn, this.sortOrder]],
          paging: true,
          searching: true,
          info: true,
          columnDefs: [
            {
              orderable: false,
              targets: 'no-sort'
            }
          ],
          fnDrawCallback: function (settings) {
            $('td.details-control').html(
              '<i class="fas fa-lg fa-plus-circle text-primary"></i>'
            );
          }
        });
      }
    },
    noteText(idx) {
      if (this.showFullText[idx] === true) {
        return this.notes[idx].text;
      }
      return this.preview(this.notes[idx].text);
    },
    preview(text) {
      let sub = text.substring(0, 200);
      if (sub.length === 200) {
        sub = sub.concat('... <i class="fas fa-angle-double-right text-primary"></i>');
      }
      return sub;
    },
    toggleText(idx) {
      this.showFullText[idx] = !this.showFullText[idx];
    },
    toggleDetails(event) {
      // TODO: Check for existence of the data table?
      // TODO: Probably should use $refs but didn't know how inside v-for
      let tr = event.target.closest('tr');
      let td = event.target.closest('td');
      let row = this.dataTable.row(tr);
      if (row.child.isShown()) {
        row.child.hide();
        $(td).html('<i class="fas fa-lg fa-plus-circle text-primary"></i>');
      } else {
        row.child(this.showNoteText(row.data()), 'details-row').show();
        $(td).html('<i class="fas fa-lg fa-minus-circle text-primary"></i>');
      }
    },
    showNoteText(d) {
      // TODO: Something better than passing data in and showing last column?
      return '<p>' + d[d.length - 1] + '</p>';
    }
  },
  watch: {
    procedures() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
