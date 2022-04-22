<template>
  <div class="note-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive">
      <table class="table table-striped table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Class</th>
            <th>Title</th>
            <th>Text</th>
            <th v-if="showVisit">Visit Occurrence</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="note in notes" :key="note.id">
            <td data-field="id">
              {{ note.id }}
            </td>
            <td data-field="noteDatetime">
              {{ note.dateTime }}
            </td>
            <td data-field="noteType">
              {{ note.noteType }}
            </td>
            <td data-field="noteClass">
              {{ note.noteClass }}
            </td>
            <td data-field="title">
              {{ note.title }}
            </td>
            <td data-field="text">
              {{ note.text }}
            </td>
            <td v-if="showVisit" data-field="visitOccurrence">
              {{ measurement.visitOccurrence }}
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
      default: 4
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
  mounted() {
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
    }
  },
  watch: {
    procedures() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
