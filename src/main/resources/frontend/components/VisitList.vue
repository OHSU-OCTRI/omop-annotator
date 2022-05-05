<template>
  <div class="visit-list">
    <h2 v-if="showHeader">Visits</h2>
    <div class="table-responsive omop-data">
      <button
        class="btn btn-outline-primary btn-sm mb-2"
        v-if="contextAvailable"
        @click="showContext()"
        title="Show the surrounding context of the selected visit"
      >
        <small>Show Context</small>
      </button>
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
          <tr
            v-for="visitOccurrence in visits"
            :key="visitOccurrence.id"
            @click="$emit('visit-selected', visitOccurrence.id)"
            :class="{ 'table-active': isSelectedVisit(visitOccurrence.id) }"
          >
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
/**
 * Comparison function to compare two visit occurrences by visitStart.
 */
function compareVisitStart(a, b) {
  if (a.visitStart < b.visitStart) return -1;
  if (a.visitStart > b.visitStart) return 1;
  return 0;
}

export default {
  props: {
    visits: {
      type: Array,
      required: true
    },
    selectedVisitId: {
      type: Number,
      default: null
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
      search: null,
      pageLength: 10
    };
  },
  mounted() {
    this.$nextTick(this.drawDataTable);
  },
  computed: {
    contextAvailable() {
      return Boolean(this.selectedVisitId && this.search);
    },
    selectedVisitIndex() {
      // index when temporally sorted
      if (this.selectedVisitId) {
        const sortedVisits = [...this.visits].sort(compareVisitStart);
        return sortedVisits.findIndex(visit => visit.id === this.selectedVisitId);
      }
      return null;
    },
    selectedVisitPage() {
      // page when sorted by visitStart.
      if (this.selectedVisitIndex) {
        return Math.floor(this.selectedVisitIndex / this.pageLength);
      }
      return null;
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
          pageLength: this.pageLength,
          searching: true,
          info: true
        });
        // Update the component search term when a datatables search is performed.
        this.dataTable.on('search.dt', () => {
          this.search = this.dataTable.search();
        });

        this.dataTable.on('length.dt', (e, settings, len) => {
          this.pageLength = len;
        });
      }
    },
    showContext() {
      // Shows the context in a linear timeline. We may also want the ability to
      // navigate to the selected record after sorting by a column.
      if (this.contextAvailable) {
        const startDateColumn = 2;
        this.dataTable
          .search('')
          .order([startDateColumn, 'asc'])
          .draw()
          .page(this.selectedVisitPage)
          .draw(false);
      }
    },
    isSelectedVisit(visitId) {
      return this.selectedVisitId && this.selectedVisitId === visitId;
    }
  },
  watch: {
    visits() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
