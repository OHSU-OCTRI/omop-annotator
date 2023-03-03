<template>
  <div class="d-flex justify-content-center" v-if="loading">
    <LoadingSpinner />
  </div>
  <div class="row" v-else>
    <div class="col-md-2 mb-4 pool-entries-column">
      <h2 v-if="showHeader" class="fs-4">Pool Entries</h2>

      <nav class="nav nav-sm nav-pills mb-2" role="group">
        <a
          v-for="filter in filterNames"
          :key="filter"
          class="nav-link text-capitalize"
          :class="{ active: filter === activeFilter }"
          @click.prevent="selectFilter(filter)"
        >
          {{ filter }}
        </a>
      </nav>

      <div class="list-group entry-judgments">
        <a
          v-for="item in filteredEntryJudgments"
          :key="item.documentId"
          href="#"
          class="list-group-item list-group-item-action"
          :title="`Show document: ${item.documentId}`"
          :class="{ active: isSelected(item) }"
          :aria-current="isSelected(item)"
          @click.prevent="selectDocument(item)"
        >
          <EntryJudgment :entry-judgment="item" :selected="isSelected(item)" />
        </a>
        <span class="instructions" v-if="filteredEntryJudgments.length === 0">None</span>
      </div>
    </div>
    <div class="col-md-10 mb-8">
      <OmopBrowser
        v-if="selectedEntryJudgment"
        :person-id="selectedEntryJudgment.documentId"
        :pool-entry-id="selectedEntryJudgment.poolEntryId"
        @judgment-saved="handleJudgment"
      />
      <LoadingSpinner v-else />
    </div>
  </div>
</template>

<script>
import EntryJudgment from './EntryJudgment';
import LoadingSpinner from './LoadingSpinner';
import OmopBrowser from './OmopBrowser';

import { contextPath } from '../utils/injection-keys';

export default {
  props: {
    poolId: {
      type: Number,
      required: true
    },
    topicId: {
      type: Number,
      required: true
    },
    showHeader: {
      type: Boolean,
      default: true
    }
  },
  inject: {
    [contextPath]: {
      default: ''
    }
  },
  components: {
    EntryJudgment,
    LoadingSpinner,
    OmopBrowser
  },
  data() {
    return {
      loading: true,
      entryJudgments: [],
      selectedEntryJudgment: null,
      filters: {
        all: () => true,
        judged: entry => entry.judgmentId !== null,
        unjudged: entry => entry.judgmentId === null
      },
      activeFilter: 'all'
    };
  },
  async mounted() {
    if (this.noEntryJudgments) {
      this.loading = true;
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.entryJudgments = await response.json();
      this.loading = false;
    }

    if (this.hasEntryJudgments && !this.selectedEntryJudgment) {
      this.selectedEntryJudgment = this.entryJudgments[0];
    }
  },
  computed: {
    hasEntryJudgments() {
      return this.entryJudgments.length > 0;
    },

    noEntryJudgments() {
      return !this.hasEntryJudgments;
    },

    url() {
      return `${this.contextPath}/data/api/pool/${this.poolId}/topic/${this.topicId}/pool_entry_judgments`;
    },
    filterNames() {
      return Object.keys(this.filters);
    },
    filteredEntryJudgments() {
      return this.entryJudgments.filter(this.filters[this.activeFilter]);
    }
  },
  methods: {
    handleJudgment(judgment) {
      const entryJudgment = this.entryJudgments.find(
        ej => ej.poolEntryId === judgment.poolEntryId
      );
      const selectedLabel = judgment.annotationLabels.find(
        al => al.id === judgment.annotationLabelId
      );
      entryJudgment.judgmentId = judgment.id;
      entryJudgment.annotation = selectedLabel.label;
    },

    isSelected(entryJudgment) {
      const { selectedEntryJudgment } = this;
      return (
        selectedEntryJudgment &&
        selectedEntryJudgment.documentId === entryJudgment.documentId
      );
    },

    selectDocument(entryJudgment) {
      this.selectedEntryJudgment = entryJudgment;
    },

    selectFilter(name) {
      if (name in this.filters) {
        this.activeFilter = name;
        const filtered = this.entryJudgments.filter(this.filters[this.activeFilter]);

        const selectionInView =
          this.selectedEntryJudgment &&
          filtered.some(
            entryJudgment =>
              entryJudgment.documentId === this.selectedEntryJudgment.documentId
          );
        if (!selectionInView && filtered.length > 0) {
          this.selectedEntryJudgment = filtered[0];
        }
      }
    }
  }
};
</script>
