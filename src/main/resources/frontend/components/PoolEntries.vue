<template>
  <div class="container pool-entries">
    <div class="row mt-4">
      <div class="col">
        <h3>{{cardTitleText}}</h3>
      </div>
      <div class="col">
        <button type="button" class="btn btn-primary float-end" @click="toggleUnjudged()">{{toggleButtonText}}</button>
      </div>
    </div>
    <table class="table table-striped table-bordered sorted mt-4">
      <thead>
        <tr>
          <th>Sort Order</th>
          <th>Person Id</th>
          <th>Judgment</th>
        </tr>
      </thead>
      <tbody v-if="showUnjudged">
        <tr v-for="entry in unjudgedEntries" :key="entry.poolEntryId">
          <td>{{ entry.sortOrder }}</td>
          <td>{{ entry.documentId }}</td>
          <td><a :href="judgmentLink(entry.poolEntryId)">Judge</a></td>
        </tr>
      </tbody>
      <tbody v-if="showJudged">
        <tr v-for="entry in judgedEntries" :key="entry.poolEntryId">
          <td>{{ entry.sortOrder }}</td>
          <td>{{ entry.documentId }}</td>
          <td>{{ entry.annotation }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
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
    }
  },
  data() {
    return {
      entries: [],
      showUnjudged: true
    };
  },
  mounted() {
    if (this.entries.length === 0) {
      fetch(this.url, { credentials: 'same-origin' })
        .then(response => response.json())
        .then(jsonObj => {
          this.entries = jsonObj;
        });
    }
  },
  computed: {
    url() {
      return `${this.contextPath}/judge/pool/${this.poolId}/topic/${this.topicId}/pool_entry_judgments`;
    },
    showJudged() {
      return !this.showUnjudged;
    },
    toggleButtonText() {
      return this.showUnjudged ? `Show Judged Entries (${this.judgedEntries.length})`: `Show Unjudged Entries (${this.unjudgedEntries.length})`
    },
    cardTitleText() {
      return this.showUnjudged ? `Unjudged Entries (${this.unjudgedEntries.length})`: `Judged Entries (${this.judgedEntries.length})`
    },
    judgedEntries() {
      return this.entries.filter(entry => entry.judgmentId !== null)
    },
    unjudgedEntries() {
      return this.entries.filter(entry => entry.judgmentId === null)
    }
  },
  methods: {
    toggleUnjudged() {
      this.showUnjudged = !this.showUnjudged;
    },
    judgmentLink(poolEntryId) {
      // TODO: Implement judgment interface. For now, redirect to the view of the person this pool entry id is for
      const personId = this.entries.find(entry => entry.poolEntryId === poolEntryId).documentId;
      return `${this.contextPath}/data/person/${personId}`;
    }
  }
};
</script>
