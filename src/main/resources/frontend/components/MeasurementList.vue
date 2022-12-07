<template>
  <div class="measurement-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped table-bordered table-sm w-100" ref="table">
        <thead>
          <tr>
            <th class="no-sort col-1"></th>
            <th>Measurement</th>
            <th>Count</th>
          </tr>
          <tr class="search-row">
            <th></th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(measurement, index) in measurements.keys()"
            :key="measurement"
            ref="measurementRows"
            class="main-row"
          >
            <td
              class="text-center details-control col-1"
              @click="toggleDetails(measurement, index)"
              title="Show measurement details"
            ></td>
            <td data-field="measurement">
              {{ measurement }}
            </td>
            <td>{{ count(measurement) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    measurements: {
      type: Map,
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
      return `Measurements${filter}`;
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
          columnDefs: [
            {
              orderable: false,
              targets: 'no-sort'
            }
          ],
          fnDrawCallback: function (settings) {
            $('td.details-control').html(
              '<i class="fas fa-lg fa-plus-circle text-primary"></i>'
            );
          },
          orderCellsTop: true,
          initComplete: function () {
            this.api()
              .columns([1])
              .every(function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                  .appendTo(
                    $('.measurement-list .search-row th').eq(column.index()).empty()
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
    },
    toggleDetails(measurement, index) {
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        let tr = this.$refs.measurementRows[index];
        let td = tr.querySelector('td');
        let row = this.dataTable.row(tr);
        if (row.child.isShown()) {
          row.child.hide();
          $(td).html('<i class="fas fa-lg fa-plus-circle text-primary"></i>');
        } else {
          row.child(this.showDetails(measurement), 'details-row').show();
          $(td).html('<i class="fas fa-lg fa-minus-circle text-primary"></i>');
        }
      }
    },
    showDetails(measurement) {
      let details = this.measurements.get(measurement);
      let table = `
        <table class='table details-table'>
        <thead>
          <tr>
            <th>Id</th>
            <th>Datetime</th>
            <th>Type</th>
            <th>Value</th>
            <th>Unit</th>
          </tr>
        </thead>
        <tbody>`;
      for (let i = 0; i < details.length; i++) {
        let measurement = details[i];
        let val = this.getValue(measurement);
        table += `
          <tr>
            <td>${measurement.id}</td>
            <td>${measurement.measurementDatetime}</td>
            <td>${measurement.measurementType}</td>
            <td>${val}</td>
            <td>${measurement.unit}</td>
          </tr>
        `;
      }
      table += '</tbody></table>';
      return table;
    },
    getValue(measurement) {
      return (
        measurement.valueSourceValue ??
        measurement.valueAsConcept ??
        measurement.valueAsNumber
      );
    },
    count(measurement) {
      return this.measurements.get(measurement).length;
    }
  },
  watch: {
    measurements() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
