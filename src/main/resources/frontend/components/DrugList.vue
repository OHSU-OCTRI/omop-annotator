<template>
  <div class="drug-list">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped-with-details table-bordered table-sm" ref="table">
        <thead>
          <tr>
            <th class="no-sort col-1"></th>
            <th>Drug</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(drug, index) in primaryDrugInfo.keys()"
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
      type: Array,
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
    },
    primaryDrugInfo() {
      return this.drugs.reduce(
        (entryMap, e) => entryMap.set(e.drug, [...(entryMap.get(e.drug) || []), e]),
        new Map()
      );
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
      let details = this.primaryDrugInfo.get(drug);
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
    }
  },
  watch: {
    drugs() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
