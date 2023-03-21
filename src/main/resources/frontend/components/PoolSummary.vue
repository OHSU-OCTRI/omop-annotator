<template>
  <div class="table-responsive">
    <table class="table table-striped table-bordered table-sm w-100" ref="table">
      <thead>
        <tr>
          <th>Topic Number</th>
          <th>Topic Narrative</th>
          <th>Completed Judgments</th>
          <th>Judge</th>
          <th class="d-none" scope="col">Full Narrative</th>
        </tr>
        <tr class="search-row">
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th class="d-none" scope="col"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, index) in summary" :key="index">
          <td>{{ row.topicNumber }}</td>
          <td
            class="col-md-8"
            @click="toggleNarrative(index)"
            v-html="narrativeText(index)"
          ></td>
          <td>{{ fractionComplete(row) }}</td>
          <td>{{ judgeName(row.judge) }}</td>
          <!-- Add a hidden column for the full narrative so it can be searched -->
          <td class="d-none" data-field="fullNarrative">
            {{ row.topicNarrative }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    summary: {
      type: Array,
      required: true
    },
    sortColumn: {
      type: Number,
      default: 0
    },
    sortOrder: {
      type: String,
      default: 'asc'
    },
    previewSize: {
      type: Number,
      default: 100
    }
  },
  data() {
    return {
      showFullText: []
    };
  },
  mounted() {
    this.showFullText = new Array(this.summary.length);
    this.showFullText.fill(false);
    this.$nextTick(this.drawDataTable);
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
              .columns([0, 3])
              .every(function () {
                const column = this;
                let select = $('<select><option value=""></option></select>')
                  .appendTo($('.search-row th').eq(column.index()).empty())
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
    fractionComplete(row) {
      return `${row.completed ? row.completed : 0} / ${row.poolSize}`;
    },
    judgeName(judge) {
      return judge ? judge : '--';
    },
    narrativeText(idx) {
      if (this.showFullText[idx] === true) {
        return this.expand(this.summary[idx].topicNarrative);
      }
      return this.preview(this.summary[idx].topicNarrative);
    },
    preview(text) {
      let sub = text.substring(0, this.previewSize);
      if (sub.length === this.previewSize) {
        sub = sub.concat('... <i class="fas fa-angle-double-right text-primary"></i>');
      }
      return sub;
    },
    expand(text) {
      if (text.length > this.previewSize) {
        text = text.concat(' <i class="fas fa-angle-double-left text-primary"></i>');
      }
      return text;
    },
    toggleNarrative(idx) {
      this.showFullText[idx] = !this.showFullText[idx];
    }
  },
  watch: {
    summary() {
      this.$nextTick(this.drawDataTable);
    }
  }
};
</script>
