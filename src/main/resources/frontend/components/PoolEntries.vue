<template>
  <div class="container pool-entries">
    <div class="row my-4">
      <div class="col">
        <h3>{{ cardTitleText }}</h3>
      </div>
      <div class="col">
        <button type="button" class="btn btn-primary float-end" @click="toggleUnjudged()">
          {{ toggleButtonText }}
        </button>
      </div>
    </div>
    <div v-if="loading">
      <LoadingSpinner/>
    </div>
    <div v-else class="table-responsive" ref="table-div">
      <table
        v-show="showUnjudged"
        :id="unjudgedTableId"
        class="table table-striped table-bordered sorted"
      >
        <thead>
          <tr>
            <th>Sort Order</th>
            <th>Person Id</th>
            <th>Judgment</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entry in unjudgedEntries" :key="entry.poolEntryId">
            <td data-field="unjudged_poolEntryId">{{ entry.sortOrder }}</td>
            <td data-field="unjudged_documentId">{{ entry.documentId }}</td>
            <td data-field="unjudged_judgment">
              <a :href="judgmentLink(entry.poolEntryId)">Judge</a>
            </td>
          </tr>
        </tbody>
      </table>
      <table
        v-show="showJudged"
        :id="judgedTableId"
        class="table table-striped table-bordered sorted"
      >
        <thead>
          <tr>
            <th>Sort Order</th>
            <th>Person Id</th>
            <th>Judgment</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entry in judgedEntries" :key="entry.poolEntryId">
            <td data-field="judged_poolEntryId">{{ entry.sortOrder }}</td>
            <td data-field="documentId">{{ entry.documentId }}</td>
            <td data-field="judgment">{{ entry.annotation }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import LoadingSpinner from './LoadingSpinner';

export default {
  props: {
    contextPath: {
      type: String,
      default: ''
    },
    poolId: {
      type: Number,
      required: true
    },
    topicId: {
      type: Number,
      required: true
    },
    sortColumn: {
      type: Number,
      default: 1
    },
    sortOrder: {
      type: String,
      default: 'asc'
    }
  },
  components: {
    LoadingSpinner
  },
  data() {
    return {
      entries: [],
      showUnjudged: true,
      loading: true
    };
  },
  async mounted() {
    if (this.entries.length === 0) {
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.entries = await response.json();
      this.loading = false;
    }
    await this.$nextTick();
    this.drawDataTable();
  },
  computed: {
    url() {
      return `${this.contextPath}/judge/pool/${this.poolId}/topic/${this.topicId}/pool_entry_judgments`;
    },
    showJudged() {
      return !this.showUnjudged;
    },
    toggleButtonText() {
      return this.showUnjudged
        ? `Show Judged Entries (${this.judgedEntries.length})`
        : `Show Unjudged Entries (${this.unjudgedEntries.length})`;
    },
    cardTitleText() {
      return this.showUnjudged
        ? `Unjudged Entries (${this.unjudgedEntries.length})`
        : `Judged Entries (${this.judgedEntries.length})`;
    },
    judgedEntries() {
      return this.entries.filter(entry => entry.judgmentId !== null);
    },
    unjudgedEntries() {
      return this.entries.filter(entry => entry.judgmentId === null);
    },
    unjudgedTableId() {
      return `pool_${this.poolId}_topic_${this.topicId}_unjudged`;
    },
    judgedTableId() {
      return `pool_${this.poolId}_topic_${this.topicId}_judged`;
    }
  },
  methods: {
    toggleUnjudged() {
      this.showUnjudged = !this.showUnjudged;
      if (typeof $.fn.DataTable === 'function') {
        const elem = this.$refs['table-div'];
        const unjudgedDataTableWrapper = elem.querySelector(
          `#${this.unjudgedTableId}_wrapper`
        );
        const judgedDataTableWrapper = elem.querySelector(
          `#${this.judgedTableId}_wrapper`
        );
        unjudgedDataTableWrapper.hidden = !unjudgedDataTableWrapper.hidden;
        judgedDataTableWrapper.hidden = !judgedDataTableWrapper.hidden;
      }
    },
    judgmentLink(poolEntryId) {
      // TODO: Implement judgment interface. For now, redirect to the view of the person this pool entry id is for
      const personId = this.entries.find(
        entry => entry.poolEntryId === poolEntryId
      ).documentId;
      return `${this.contextPath}/data/person/${personId}`;
    },
    drawDataTable() {
      // Format with the the datatables library if it is available.
      if (typeof $.fn.DataTable === 'function') {
        $(`#${this.unjudgedTableId}`).DataTable({
          order: [[this.sortColumn, this.sortOrder]],
          paging: true,
          searching: true,
          info: true
        });
        $(`#${this.judgedTableId}`).DataTable({
          order: [[this.sortColumn, this.sortOrder]],
          paging: true,
          searching: true,
          info: true
        });
        const elem = this.$refs['table-div'];
        elem.querySelector(`#${this.unjudgedTableId}_wrapper`).hidden = false;
        elem.querySelector(`#${this.judgedTableId}_wrapper`).hidden = true;
      }
    }
  }
};
</script>
