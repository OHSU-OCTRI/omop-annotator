<template>
  <div class="visit-list">
    <h2 v-if="showHeader">Visits</h2>
    <div v-if="loading">
      <span class="ms-2"></span>
      <span
        class="spinner-border spinner-border-sm text-secondary ms-auto"
        role="status"
        aria-hidden="true"
      >
      </span>
      <span class="ms-2">Loading...</span>
    </div>
    <div v-else class="table-responsive">
      <table :id="tableId" class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Id</th>
            <th>Visit Type</th>
            <th>Visit Start</th>
            <th>Visit End</th>
            <th>Visit Source</th>
            <th>Admitting Source</th>
            <th>Discharged To</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td>Loading...</td>
          </tr>
          <tr v-for="visit in visits" :key="visit.id" :data-visit-id="visit.id">
            <td data-field="id">{{ visit.id }}</td>
            <td data-field="visitType">{{ visit.visitType.name }}</td>
            <td data-field="visitStart">{{ visit.visitStart }}</td>
            <td data-field="visitEnd">{{ visit.visitEnd }}</td>
            <td data-field="visitSource">{{ visit.visitSource }}</td>
            <td data-field="admittingSource">{{ visit.admittingSource }}</td>
            <td data-field="dischargedTo">{{ visit.dischargedTo }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
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
