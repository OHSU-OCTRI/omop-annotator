<template>
  <div class="d-flex justify-content-center" v-if="loading">
    <LoadingSpinner />
  </div>
  <div class="row" v-else>
    <div class="col-2">
      <h2 class="fs-4">Pool Entries</h2>
      <!-- TODO: filter by judged or unjudged -->
      <!-- TODO: pagination or scrolling -->
      <div class="list-group">
        <a
          v-for="item in entryJudgments"
          :key="item.documentId"
          href="#"
          class="list-group-item list-group-item-action"
          :class="{ active: isSelected(item) }"
          :aria-current="isSelected(item)"
          @click.prevent="selectDocument(item)"
        >
          <EntryJudgment :entry-judgment="item" :selected="isSelected(item)" />
        </a>
      </div>
    </div>
    <div class="col">
      <OmopBrowser
        v-if="selectedDocumentId"
        :context-path="contextPath"
        :person-id="selectedDocumentId"
      />
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
      selectedDocumentId: null
    };
  },
  async mounted() {
    if (this.noEntryJudgments) {
      this.loading = true;
      const response = await fetch(this.url, { credentials: 'same-origin' });
      this.entryJudgments = await response.json();
      this.loading = false;
    }

    if (this.hasEntryJudgments && !this.selectedDocumentId) {
      this.selectedDocumentId = this.entryJudgments[0].documentId;
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
      return `${this.contextPath}/judge/pool/${this.poolId}/topic/${this.topicId}/pool_entry_judgments`;
    }
  },
  methods: {
    isSelected(entryJudgment) {
      return this.selectedDocumentId === entryJudgment.documentId;
    },

    selectDocument(entryJudgment) {
      if (this.selectedDocumentId !== entryJudgment.documentId) {
        this.selectedDocumentId = entryJudgment.documentId;
      }
    }
  }
};
</script>
