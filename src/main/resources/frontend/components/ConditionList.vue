<template>
  <div class="condition-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div v-if="loading">
      <LoadingSpinner/>
    </div>
    <div v-else class="table-responsive">
      <table :id="tableId" class="table table-striped table-bordered sorted">
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
      conditions: [],
      loading: true
    };
  },
  async mounted() {
    if (this.conditions.length === 0) {
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.conditions = await response.json();
      this.loading = false;
    }
    await this.$nextTick();
    this.drawDataTable();
  },
  computed: {
    url() {
      // TODO: filter by visit if visitId is present
      return `${this.contextPath}/data/person/summary/${this.personId}/conditions`;
    },
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Condition Occurrences${filter}`;
    },
    tableId() {
      return `patient_${this.personId}_visit_${this.visitId}_condition_data`;
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
