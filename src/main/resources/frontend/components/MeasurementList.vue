<template>
  <div class="measurement-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div v-if="loading">
      <LoadingSpinner/>
    </div>
    <div v-else class="table-responsive">
      <table :id="tableId" class="table table-striped table-bordered sorted">
        <thead>
          <tr>
            <th>Id</th>
            <th>Measurement</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Value</th>
            <th>Unit</th>
            <th>Visit Occurrence</th>
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
            <td data-field="visitOccurrence">
              {{ measurement.visitOccurrence }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import LoadingSpinner from './LoadingSpinner';

export default {
  props: {
    contextPath: {
      type: String,
      default: ''
    },
    personId: {
      type: Number,
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
  components: {
    LoadingSpinner
  },
  data() {
    return {
      measurements: [],
      loading: true
    };
  },
  async mounted() {
    if (this.measurements.length === 0) {
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.measurements = await response.json();
      this.loading = false;
    }
    await this.$nextTick(this.drawDataTable);
  },
  computed: {
    url() {
      // TODO: filter by visit if visitId is present
      return `${this.contextPath}/data/person/summary/${this.personId}/measurements`;
    },
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Measurements${filter}`;
    },
    tableId() {
      return `patient_${this.personId}_visit_${this.visitId}_measurement_data`;
    }
  },
  methods: {
    drawDataTable() {
      // Format with the the datatables library if it is available.
      if (typeof $.fn.DataTable === 'function') {
        $(`#${this.tableId}`).DataTable({
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
  }
};
</script>
