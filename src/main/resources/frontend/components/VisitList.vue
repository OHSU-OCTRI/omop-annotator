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
            <th>Pin</th>
            <th v-for="field in fieldsToShow" :key="field.fieldName" scope="col">
              {{ field.columnDisplay }}
            </th>
          </tr>
          <tr v-if="indexesToFilter.length > 0" class="search-row">
            <th></th>
            <th v-for="field in fieldsToShow" :key="field.fieldName" scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="visitOccurrence in visits"
            :key="visitOccurrence.id"
            @click="$emit('visit-selected', visitOccurrence.id)"
            :class="{ 'table-active': isSelectedVisit(visitOccurrence.id) }"
          >
            <td
              :class="isPinned(visitOccurrence.id) ? 'text-primary' : 'text-unpinned'"
              @click="togglePin(visitOccurrence.id)"
            >
              <i class="fas fa-thumbtack"></i>
            </td>
            <td
              v-for="field in fieldsToShow"
              :key="field.fieldName"
              :data-field="field.fieldName"
              v-html="visitOccurrence[field.fieldName]"
            ></td>
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
    poolEntryId: {
      type: Number,
      required: true
    },
    personId: {
      type: Number,
      required: true
    },
    visits: {
      type: Array,
      required: true
    },
    configuration: {
      type: Array,
      required: true
    },
    pins: {
      type: Array,
      required: true
    },
    selectedVisitId: {
      type: Number,
      default: null
    },
    sortColumn: {
      type: Number,
      default: 1
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
          'medication',
          'any'
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
  emits: ['visit-selected', 'pin-saved', 'pin-deleted'],
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
    },
    fieldsToShow() {
      return this.configuration.filter(f => f.visible === true);
    },
    indexesToFilter() {
      const b = this.fieldsToShow
        .map((item, index) => ({ index, ...item }))
        .filter(f => f.filter === true)
        .map(f => f.index + 1); // Add 1 to account for Pin column
      return b;
    },
    pinnedVisits() {
      return this.pins.filter(pin => pin.entity === 'Visit').map(pin => pin.entityId);
    }
  },
  methods: {
    drawDataTable() {
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        if (this.dataTable) {
          this.dataTable.clear().destroy();
        }
        const indexesToFilter = this.indexesToFilter;
        this.dataTable = $(this.$refs.table).DataTable({
          columnDefs: [
            {
              targets: 0,
              orderable: false
            }
          ],
          order: [[this.sortColumn, this.sortOrder]],
          paging: true,
          pageLength: this.pageLength,
          searching: true, // this must be true to use the api call
          info: true,
          dom: 'lrtip', // exclude default search controls from the dom
          orderCellsTop: true,
          initComplete: function () {
            if (indexesToFilter.length > 0) {
              this.api()
                .columns(indexesToFilter)
                .every(function () {
                  const column = this;
                  let select = $('<select><option value=""></option></select>')
                    .appendTo($('.visit-list .search-row th').eq(column.index()).empty())
                    .on('change', function () {
                      const val = $.fn.dataTable.util.escapeRegex($(this).val());

                      column.search(val ? '^' + val + '$' : '', true, false).draw();
                    });

                  column
                    .data()
                    .unique()
                    .sort()
                    .each(function (d, j) {
                      select.append('<option value="' + d + '">' + d + '</option>');
                    });
                });
            }
          }
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
    },
    isPinned(visitId) {
      return this.pinnedVisits.includes(visitId);
    },
    togglePin(visitId) {
      if (this.isPinned(visitId)) {
        this.$emit(
          'pin-deleted',
          this.pins.find(p => p.entity === 'Visit' && p.entityId === visitId)
        );
      } else {
        const obj = {
          userId: null,
          poolEntryId: this.poolEntryId,
          entityId: visitId,
          entity: 'Visit',
          visitId: null
        };
        this.$emit('pin-saved', obj);
      }
    }
  },
  watch: {
    visits() {
      this.$nextTick(this.drawDataTable);
    },
    pins() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
