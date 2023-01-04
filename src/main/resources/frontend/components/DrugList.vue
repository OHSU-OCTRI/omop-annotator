<template>
  <div class="drug-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table
        class="table table-striped-with-details table-bordered table-sm w-100"
        ref="table"
      >
        <thead>
          <tr>
            <th class="no-sort col-1"></th>
            <th>Drug</th>
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
            v-for="(drug, index) in drugs.keys()"
            :key="drug"
            ref="drugRows"
            class="main-row"
          >
            <td
              class="text-center details-control col-1"
              @click="toggleDetails(drug, index)"
              title="Show drug details"
            ></td>
            <td data-field="drug">
              {{ drug }}
            </td>
            <td>{{ count(drug) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    drugs: {
      type: Map,
      required: true
    },
    visitId: {
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
      return `Medications${filter}`;
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
                const column = this;
                let select = $('<select><option value=""></option></select>')
                  .appendTo($('.drug-list .search-row th').eq(column.index()).empty())
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
        });
      }
    },
    toggleDetails(drug, index) {
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        let tr = this.$refs.drugRows[index];
        let td = tr.querySelector('td');
        let row = this.dataTable.row(tr);
        if (row.child.isShown()) {
          row.child.hide();
          $(td).html('<i class="fas fa-lg fa-plus-circle text-primary"></i>');
        } else {
          row.child(this.showDetails(drug), 'details-row').show();
          $(td).html('<i class="fas fa-lg fa-minus-circle text-primary"></i>');
        }
      }
    },
    showDetails(drug) {
      let details = this.drugs.get(drug);
      let table = `
        <table class='table details-table'>
        <thead>
        <tr>
          <th>Id</th>
          <th>Drug Type</th>
          <th>Start</th>
          <th>End</th>
          <th>Stop Reason</th>
          <th>Quantity</th>
        </tr>
        </thead>
        <tbody>`;
      for (let i = 0; i < details.length; i++) {
        let drugRecord = details[i];
        table += `
          <tr>
            <td>${drugRecord.id}</td>
            <td>${drugRecord.drugType}</td>
            <td>${drugRecord.drugStart}</td>
            <td>${drugRecord.drugEnd}</td>
            <td>${drugRecord.stopReason}</td>
            <td>${drugRecord.quantity}</td>
          </tr>
        `;
      }
      table += '</tbody></table>';
      return table;
    },
    count(drug) {
      return this.drugs.get(drug).length;
    }
  },
  watch: {
    drugs() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
