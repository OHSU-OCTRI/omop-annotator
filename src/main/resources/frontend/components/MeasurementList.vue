<template>
  <div class="measurement-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Measurement</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Value</th>
            <th>Unit</th>
            <th v-if="showVisit">Visit Occurrence</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="measurement in measurements" :key="measurement.id">
            <td data-field="id">
              {{ measurement.id }}
            </td>
            <td data-field="measurement">
              {{ measurement.measurement }}
            </td>
            <td data-field="measurementDateTime">
              {{ measurement.measurementDatetime }}
            </td>
            <td data-field="measurementType">
              {{ measurement.measurementType }}
            </td>
            <td data-field="measurementValue">
              {{ getValue(measurement) }}
            </td>
            <td data-field="measurementUnit">
              {{ measurement.unit }}
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
    measurements: {
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
      return `Measurements${filter}`;
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
    getValue(measurement) {
      return measurement.valueAsNumber ?? measurement.valueAsConcept;
    }
  },
  watch: {
    measurements() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
