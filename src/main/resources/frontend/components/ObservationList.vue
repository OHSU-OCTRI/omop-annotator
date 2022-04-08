<template>
  <div class="observation-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div v-if="loading">
      <LoadingSpinner/>
    </div>
    <div v-else class="table-responsive">
      <table :id="tableId" class="table table-striped table-bordered sorted">
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
              {{ observation.observation?.name }}
            </td>
            <td data-field="observationDateTime">
              {{ observation.formattedObservationDate }}
            </td>
            <td data-field="conditionEnd">
              {{ observation.observationType?.name }}
            </td>
            <td data-field="valueAsString">
              {{ observation.valueAsString }}
            </td>
            <td data-field="visitOccurrence">
              {{ observation.visitOccurrence?.id }}
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
      observations: [],
      loading: true
    };
  },
  async mounted() {
    if (this.observations.length === 0) {
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.observations = await response.json();
      this.loading = false;
    }
    await this.$nextTick(this.drawDataTable);
  },
  computed: {
    url() {
      // TODO: filter by visit if visitId is present
      return `${this.contextPath}/data/person/summary/${this.personId}/observations`;
    },
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Observations${filter}`;
    },
    tableId() {
      return `patient_${this.personId}_visit_${this.visitId}_observation_data`;
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
    }
  }
};
</script>
