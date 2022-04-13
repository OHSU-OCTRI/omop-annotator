<template>
  <div class="visit-list">
    <h2 v-if="showHeader">Visits</h2>
    <div class="table-responsive">
      <table class="table table-striped table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Visit Type</th>
            <th>Visit Start</th>
            <th>Visit End</th>
            <th>Provider</th>
            <th>Care Site</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="visitOccurrence in visits" :key="visitOccurrence.id">
            <td data-field="id">
              {{ visitOccurrence.id }}
            </td>
            <td data-field="visitType">
              {{ visitOccurrence.visitType }}
            </td>
            <td data-field="visitStart">
              {{ visitOccurrence.visitStart }}
            </td>
            <td data-field="visitEnd">
              {{ visitOccurrence.visitEnd }}
            </td>
            <td data-field="provider">
              {{ visitOccurrence.providerName }}
            </td>
            <td data-field="careSite">
              {{ visitOccurrence.careSiteName }}
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
    visits: {
      type: Array,
      required: true
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
    }
  },
  mounted() {
    this.$nextTick(this.drawDataTable);
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
    visits() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
