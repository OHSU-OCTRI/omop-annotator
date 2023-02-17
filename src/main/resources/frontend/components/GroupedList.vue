<template>
  <div :class="componentClass">
    <h2 v-if="showHeader">{{ header }}</h2>
    <div class="table-responsive omop-data">
      <table class="table table-striped table-bordered table-sm w-100" ref="table">
        <thead>
          <tr>
            <th class="no-sort col-1"></th>
            <th>{{ this.itemType }}</th>
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
            v-for="(item, index) in items.keys()"
            :key="item"
            ref="itemRows"
            class="main-row"
          >
            <td
              class="text-center details-control col-1"
              @click="toggleDetails(item, index)"
              title="Show item details"
            ></td>
            <td data-field="item">
              {{ item }}
            </td>
            <td>{{ count(item) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    items: {
      type: Map,
      required: true
    },
    configuration: {
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
      return `${this.itemType}s${filter}`;
    },
    componentClass() {
      return `${this.itemType.toLowerCase()}-list`;
    },
    searchRowSelector() {
      return `.${this.componentClass} .search-row th`;
    },
    anchorFieldName() {
      return `${this.itemType.toLowerCase()}`;
    },
    visibleDetailFields() {
      return this.configuration.filter(
        f => f.visible === true && f.fieldName !== this.anchorFieldName
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

        const searchRowSelector = this.searchRowSelector;
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
        });
      }
    },
    toggleDetails(item, index) {
      if (typeof $.fn.DataTable === 'function' && this.$refs.table) {
        let tr = this.$refs.itemRows[index];
        let td = tr.querySelector('td');
        let row = this.dataTable.row(tr);
        if (row.child.isShown()) {
          row.child.hide();
          $(td).html('<i class="fas fa-lg fa-plus-circle text-primary"></i>');
        } else {
          row.child(this.showDetails(item), 'details-row').show();
          $(td).html('<i class="fas fa-lg fa-minus-circle text-primary"></i>');
        }
      }
    },
    showDetails(item) {
      let details = this.items.get(item);
      let table = `
        <table class='table details-table'>
        <thead>
          <tr>`;

      for (let i = 0; i < this.visibleDetailFields.length; i++) {
        table += `<th>${this.visibleDetailFields[i].columnDisplay}</th>`;
      }

      table += `
          </tr>
        </thead>
        <tbody>`;

      for (let i = 0; i < details.length; i++) {
        let item = details[i];
        table += `<tr>`;
        for (let j = 0; j < this.visibleDetailFields.length; j++) {
          table += `<td>${item[this.visibleDetailFields[j].fieldName]}</td>`;
        }
        table += `</tr>`;
      }
      table += '</tbody></table>';
      return table;
    },
    count(item) {
      return this.items.get(item).length;
    }
  },
  watch: {
    items() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
