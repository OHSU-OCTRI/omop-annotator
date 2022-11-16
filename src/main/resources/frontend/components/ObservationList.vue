<template>
  <div class="observation-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <!-- Width must be declared to circumvent DataTables bug when there are two header rows -->
      <table class="table table-striped table-bordered table-sm w-100" ref="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Observation</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Value</th>
            <th v-if="showVisit">Visit Occurrence</th>
          </tr>
          <tr class="search-row">
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th v-if="showVisit"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="observation in observations" :key="observation.id">
            <td data-field="id">
              {{ observation.id }}
            </td>
            <td data-field="observation">
              {{ observation.name }}
            </td>
            <td data-field="observationDatetime">
              {{ observation.date }}
            </td>
            <td data-field="observationType">
              {{ observation.type }}
            </td>
            <td data-field="valueAsString">
              {{ observation.value }}
            </td>
            <td v-if="showVisit" data-field="visitOccurrence">
              {{ observation.visitOccurrence }}
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
    observations: {
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
  mounted() {
    this.$nextTick(this.drawDataTable);
  },
  computed: {
    header() {
      const filter = this.visitId ? ` for visit ${this.visitId}` : '';
      return `Observations${filter}`;
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
          searching: true,
          info: true,
          orderCellsTop: true,
          initComplete: function () {
            this.api()
              .columns([1, 3, 4])
              .every(function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                  .appendTo(
                    $('.observation-list table thead tr:eq(1) th')
                      .eq(column.index())
                      .empty()
                  )
                  .on('change', function () {
                    var val = $.fn.dataTable.util.escapeRegex($(this).val());

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
        });
      }
    }
  },
  watch: {
    observations() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
