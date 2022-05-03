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
      selectedEntryJudgment: null
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
      return `${this.contextPath}/judge/pool/${this.poolId}/topic/${this.topicId}/pool_entry_judgments`;
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
    }
  }
};
</script>
