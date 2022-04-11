<template>
  <div class="procedure-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div v-if="loading">
      <LoadingSpinner/>
    </div>
    <div v-else class="table-responsive">
      <table :id="tableId" class="table table-striped table-bordered sorted">
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
              {{ procedure.procedure?.name }}
            </td>
            <td data-field="procedureDatetime">
              {{ procedure.procedureDatetime }}
            </td>
            <td data-field="procedureType">
              {{ procedure.procedureType?.name }}
            </td>
            <td data-field="quantity">
              {{ procedure.quantity }}
            </td>
            <td data-field="visitOccurrence">
              {{ procedure.visitOccurrence?.id }}
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
      procedures: [],
      loading: true
    };
  },
  async mounted() {
    if (this.procedures.length === 0) {
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.procedures = await response.json();
      this.loading = false;
    }
    await this.$nextTick();
    this.drawDataTable();
  },
  computed: {
    url() {
      // TODO: filter by visit if visitId is present
      return `${this.contextPath}/data/person/summary/${this.personId}/procedures`;
    },
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Procedures${filter}`;
    },
    tableId() {
      return `patient_${this.personId}_visit_${this.visitId}_procedure_data`;
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
