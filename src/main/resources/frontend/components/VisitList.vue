<template>
  <div class="visit-list">
    <h2 v-if="showHeader">Visits</h2>
    <VisitTimeline :visits="visits" @date-selected="setSelectedDate" />
    <div class="table-responsive omop-data">
      <div v-if="dataTable" class="d-flex justify-content-center">
        <div class="row gx-2 mb-2">
          <div class="col-auto">
            <select
              id="visit_search_filter_entity"
              class="form-select form-select-sm"
              v-model="searchEntity"
            >
              <option
                v-for="option in searchEntities"
                :key="option"
                v-bind:value="option"
              >
                {{ option }}
              </option>
            </select>
          </div>
          <div class="col-auto">
            <div class="input-group input-group-sm">
              <span class="input-group-text">contains</span>
              <input
                id="search_term"
                class="form-control form-control-sm"
                type="text"
                v-model="searchTerm"
                v-on:keyup.enter="search()"
              />
            </div>
          </div>
          <div class="col-auto">
            <LoadingSpinner v-if="searching" class="d-inline" />
            <span v-else>
              <button type="submit" class="btn btn-sm btn-primary" @click="search()">
                Search
              </button>
              <button type="button" class="btn btn-sm btn-link" @click="clearSearch()">
                Clear
              </button>
            </span>
          </div>
        </div>
      </div>

      <table class="table table-striped table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Visit Type</th>
            <th>Visit Source Value</th>
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
            <td data-field="visitSourceValue">
              {{ visitOccurrence.visitSourceValue }}
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
  if (a.visitStart === null && b.visitStart === null) return 0;
  if (a.visitStart === null && b.visitStart !== null) return -1;
  if (a.visitStart !== null && b.visitStart === null) return 1;
  if (a.visitStart < b.visitStart) return -1;
  if (a.visitStart > b.visitStart) return 1;
  return 0;
}
import { contextPath } from '../utils/injection-keys';
import OmopApi from '../utils/omop-api';
import LoadingSpinner from './LoadingSpinner';
import VisitTimeline from './VisitTimeline';

export default {
  props: {
    personId: {
      type: Number,
      required: true
    },
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
    },
    searchEntities: {
      type: Array,
      default: () => {
        return [
          'visit',
          'condition',
          'procedure',
          'observation',
          'measurement',
          'note',
          'medication'
        ];
      }
    }
  },
  inject: {
    [contextPath]: {
      default: ''
    }
  },
  components: {
    LoadingSpinner,
    VisitTimeline
  },
  data() {
    return {
      dataTable: null,
      omopApi: null,
      pageLength: 10,
      searchEntity: 'visit',
      searchTerm: '',
      searchResults: null,
      searching: false
    };
  },
  mounted() {
    if (this.omopApi === null) {
      this.omopApi = new OmopApi(this.contextPath);
    }
    this.$nextTick(this.drawDataTable);
  },
  computed: {
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
      return 0;
    }
  },
  methods: {
    drawDataTable() {
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        if (this.dataTable) {
          this.dataTable.clear().destroy();
        }
        this.dataTable = $(this.$refs.table).DataTable({
          order: [[this.sortColumn, this.sortOrder]],
          paging: true,
          pageLength: this.pageLength,
          searching: true, // this must be true to use the api call
          info: true,
          dom: 'lrtip' // exclude default search controls from the dom
        });
        this.dataTable.on('length.dt', (e, settings, len) => {
          this.pageLength = len;
        });
      }
    },
    isSelectedVisit(visitId) {
      return this.selectedVisitId && this.selectedVisitId === visitId;
    },
    async search() {
      // Datatables could be made optional with some refactoring to use a `renderedVisits`
      // computed property which filters on `searchResults`.
      if (this.dataTable) {
        this.searching = true;
        if (this.searchEntity === 'visit') {
          // search the datatable
          this.searchResults = null;
          // clear previous search
          this.dataTable.column(0).search('');
          this.dataTable.search(this.searchTerm).draw();
        } else if (this.searchTerm.length > 0) {
          // clear previous visit search
          this.dataTable.search('');
          // use the API to search the selected child entity
          this.searchResults = await this.omopApi.searchPersonData(
            this.personId,
            this.searchEntity,
            this.searchTerm
          );
          // The returned visit ids are converted to a regex so we can use the
          // datatables functionality for filtering the rows.
          const re = `^(${this.searchResults.join('|')})$`;
          this.dataTable.column(0).search(re, true, false).draw();
        }
        this.searching = false;
      }
    },
    clearSearch() {
      this.searchEntity = 'visit';
      this.searchTerm = '';
      this.searchResults = null;

      if (this.dataTable) {
        // Clear the filter, sort by visitStart, and goto the selectedVisit page.
        const startDateColumn = 2;
        this.dataTable.column(0).search('');
        this.dataTable
          .search('')
          .order([startDateColumn, 'asc'])
          .draw()
          .page(this.selectedVisitPage)
          .draw(false);
      }
    },
    setSelectedDate(date) {
      // TODO: filter visits with the given date
    }
  },
  watch: {
    visits() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
