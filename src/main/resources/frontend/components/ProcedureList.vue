<template>
  <div class="procedure-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive">
      <table class="table table-striped table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Procedure</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Quantity</th>
            <th>Visit Occurrence</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="procedure in procedures" :key="procedure.id">
            <td data-field="id">
              {{ procedure.id }}
            </td>
            <td data-field="procedure">
              {{ procedure.procedure }}
            </td>
            <td data-field="procedureDatetime">
              {{ procedure.date }}
            </td>
            <td data-field="procedureType">
              {{ procedure.procedureType }}
            </td>
            <td data-field="quantity">
              {{ procedure.quantity }}
            </td>
            <td data-field="visitOccurrence">
              {{ procedure.visitOccurrence }}
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
    procedures: {
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
      return `Procedures${filter}`;
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
