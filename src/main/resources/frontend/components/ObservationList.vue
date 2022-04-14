<template>
  <div class="observation-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive">
      <table class="table table-striped table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Observation</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Value</th>
            <th>Visit Occurrence</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="observation in observations" :key="observation.id">
            <td data-field="id">
              {{ observation.id }}
            </td>
            <td data-field="observation">
              {{ observation.name }}
            </td>
            <td data-field="observationDatetime">
              {{ observation.date }}
            </td>
            <td data-field="observationType">
              {{ observation.type }}
            </td>
            <td data-field="valueAsString">
              {{ observation.value }}
            </td>
            <td data-field="visitOccurrence">
              {{ observation.visitOccurrence }}
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
    observations: {
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
      return `Observations${filter}`;
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
    observations() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
