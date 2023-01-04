<template>
  <div class="procedure-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped table-bordered table-sm w-100" ref="table">
        <thead>
          <tr>
            <th v-for="field in fieldsToShow" :key="field.field">
              {{ field.display }}
            </th>
            <th v-if="showVisit">Visit Occurrence</th>
          </tr>
          <tr v-if="indexesToFilter.length > 0" class="search-row">
            <th v-for="field in fieldsToShow" :key="field.field"></th>
            <th v-if="showVisit"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="procedure in procedures" :key="procedure.id">
            <td
              v-for="field in fieldsToShow"
              :key="field.field"
              :data-field="field.field"
              v-html="procedure[field.field]"
            ></td>
            <td v-if="showVisit" data-field="visitOccurrence">
              {{ procedure.visitOccurrence }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    procedures: {
      type: Array,
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
  data() {
    return {
      configuration: [
        { field: 'id', display: 'Id', filter: false, show: true },
        { field: 'procedure', display: 'Procedure', filter: true, show: true },
        { field: 'procedureSource', display: 'Source', filter: false, show: false },
        { field: 'date', display: 'Date/Time', filter: false, show: true },
        { field: 'procedureType', display: 'Type', filter: true, show: true },
        { field: 'quantity', display: 'Quantity', filter: false, show: false }
      ]
    };
  },
  mounted() {
    this.$nextTick(this.drawDataTable);
  },
  computed: {
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Procedures${filter}`;
    },
    fieldsToShow() {
      return this.configuration.filter(f => f.show === true);
    },
    indexesToFilter() {
      const b = this.fieldsToShow
        .map((item, index) => ({ index, ...item }))
        .filter(f => f.filter === true)
        .map(f => f.index);
      return b;
    }
  },
  methods: {
    drawDataTable() {
      // Format with the datatables library if it is available.
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        if (this.dataTable) {
          this.dataTable.clear().destroy();
        }

        const indexesToFilter = this.indexesToFilter;
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
                    .appendTo(
                      $('.procedure-list .search-row th').eq(column.index()).empty()
                    )
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
    procedures() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
