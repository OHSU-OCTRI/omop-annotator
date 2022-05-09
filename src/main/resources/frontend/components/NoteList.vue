<template>
  <div class="note-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped table-bordered table-sm w-100" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Title</th>
            <th class="col-md-8">Text</th>
            <th v-if="showVisit">Visit Occurrence</th>
            <th class="d-none">Full Text</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(note, idx) in notes" :key="note.id">
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
          info: true
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
    }
  },
  watch: {
    procedures() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
