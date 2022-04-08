<template>
  <div class="visit-list">
    <h2 v-if="showHeader">Visits</h2>
    <div v-if="loading">
      <LoadingSpinner/>
    </div>
    <div v-else class="table-responsive">
      <table :id="tableId" class="table table-striped table-bordered">
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
  components: {
    LoadingSpinner
  },
  data() {
    return {
      visits: [],
      loading: true
    };
  },
  async mounted() {
    if (this.visits.length === 0) {
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.visits = await response.json();
      this.loading = false;
    }
    await this.$nextTick(this.drawDataTable);
  },
  computed: {
    url() {
      return `${this.contextPath}/data/person/summary/${this.personId}/visits`;
    },
    tableId() {
      return `patient_${this.personId}_visit_data`;
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
