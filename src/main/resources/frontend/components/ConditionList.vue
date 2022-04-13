<template>
  <div class="condition-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive">
      <table class="table table-striped table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Condition</th>
            <th>Type</th>
            <th>Start</th>
            <th>End</th>
            <th>Visit</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="conditionOccurrence in conditions" :key="conditionOccurrence.id">
            <td data-field="id">
              {{ conditionOccurrence.id }}
            </td>
            <td data-field="condition">
              {{ conditionOccurrence.condition }}
            </td>
            <td data-field="conditionType">
              {{ conditionOccurrence.conditionType }}
            </td>
            <td data-field="conditionStart">
              {{ conditionOccurrence.conditionStart }}
            </td>
            <td data-field="conditionEnd">
              {{ conditionOccurrence.conditionEnd }}
            </td>
            <td data-field="visitOccurrence">
              {{ conditionOccurrence.visitOccurrence }}
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
    conditions: {
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
    }
  },
  mounted() {
    this.$nextTick(this.drawDataTable);
  },
  computed: {
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Condition Occurrences${filter}`;
    }
  },
  methods: {
    drawDataTable() {
      // Format with the the datatables library if it is available.
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
    conditions() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
