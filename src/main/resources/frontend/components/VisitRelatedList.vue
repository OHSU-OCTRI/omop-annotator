<template>
  <div :class="componentClass">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped table-bordered table-sm w-100" ref="table">
        <thead>
          <tr>
            <th v-for="field in fieldsToShow" :key="field.fieldName">
              {{ field.columnDisplay }}
            </th>
            <th v-if="showVisit">Visit Occurrence</th>
          </tr>
          <tr v-if="indexesToFilter.length > 0" class="search-row">
            <th v-for="field in fieldsToShow" :key="field.fieldName"></th>
            <th v-if="showVisit"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in items" :key="item.id">
            <td
              v-for="field in fieldsToShow"
              :key="field.fieldName"
              :data-field="field.fieldName"
              v-html="item[field.fieldName]"
            ></td>
            <td v-if="showVisit" data-field="visitOccurrence">
              {{ item.visitOccurrence }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { contextPath } from '../utils/injection-keys';
export default {
  props: {
    items: {
      type: Array,
      required: true
    },
    itemType: {
      type: String,
      required: true
    },
    visitId: {
      type: Number,
      default: null
    },
    sortColumn: {
      type: Number,
      default: 0
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
  inject: {
    [contextPath]: {
      default: ''
    }
  },
  data() {
    return {
      configuration: []
    };
  },
  mounted() {
    this.getDisplayConfig();
  },
  computed: {
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `${this.itemType}s${filter}`;
    },
    fieldsToShow() {
      return this.configuration.filter(f => f.visible === true);
    },
    indexesToFilter() {
      const b = this.fieldsToShow
        .map((item, index) => ({ index, ...item }))
        .filter(f => f.filter === true)
        .map(f => f.index);
      return b;
    },
    componentClass() {
      return `${this.itemType.toLowerCase()}-list`;
    },
    searchRowSelector() {
      return `.${this.componentClass} .search-row th`;
    }
  },
  methods: {
    async getDisplayConfig() {
      const res = await fetch(`${this.contextPath}/display/${this.itemType}`);
      const finalRes = await res.json();
      this.configuration = finalRes;
      this.$nextTick(this.drawDataTable);
    },
    drawDataTable() {
      // Format with the datatables library if it is available.
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        if (this.dataTable) {
          this.dataTable.clear().destroy();
        }

        const indexesToFilter = this.indexesToFilter;
        const searchRowSelector = this.searchRowSelector;
        this.dataTable = $(this.$refs.table).DataTable({
          order: [[this.sortColumn, this.sortOrder]],
          paging: true,
          searching: true,
          info: true,
          orderCellsTop: true,
          initComplete: function () {
            if (indexesToFilter.length > 0) {
              this.api()
                .columns(indexesToFilter)
                .every(function () {
                  const column = this;
                  let select = $('<select><option value=""></option></select>')
                    .appendTo($(searchRowSelector).eq(column.index()).empty())
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
      }
    }
  },
  watch: {
    items() {
      console.log('Something about items has changed');
      this.getDisplayConfig();
    }
  }
};
</script>
