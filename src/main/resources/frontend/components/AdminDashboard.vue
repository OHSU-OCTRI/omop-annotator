<template>
  <div class="row">
    <div class="form-group">
      <label for="topicSet" class="form-label required-field">Topic Set</label>
      <select
        class="form-select"
        id="topicSet"
        name="topicSet"
        @change="changeTopicSet($event)"
        required
      >
        <option v-for="topicSet in topicSets" :key="topicSet.id" :value="topicSet.id">
          {{ topicSet.name }}
        </option>
      </select>
    </div>
  </div>
  <h2>Judgment Progress Dashboard</h2>
  <div class="d-flex justify-content-center" v-if="loadingSummary">
    <LoadingSpinner />
  </div>
  <TopicSetSummary v-else :summary="summary" />
</template>

<script>
import TopicSetSummary from './TopicSetSummary';
import LoadingSpinner from './LoadingSpinner';
import { contextPath } from '../utils/injection-keys';
export default {
  inject: {
    [contextPath]: {
      default: ''
    }
  },
  components: {
    TopicSetSummary,
    LoadingSpinner
  },
  data() {
    return {
      topicSets: [],
      selectedTopicSetId: null,
      summary: [],
      loadingSummary: false
    };
  },
  async mounted() {
    if (!this.topicSets.length > 0) {
      const response = await fetch(this.topicSetUrl, { credentials: 'same-origin' });
      this.topicSets = await response.json();
    }

    if (this.topicSets.length > 0 && !this.selectedTopicSet) {
      this.selectedTopicSetId = this.topicSets[0].id;
    }

    await this.loadTopicSet();
  },
  computed: {
    topicSetUrl() {
      return `${this.contextPath}/admin/api/topic_sets`;
    },
    summaryUrl() {
      return `${this.contextPath}/admin/api/topic_set/${this.selectedTopicSetId}/summary`;
    }
  },
  methods: {
    async changeTopicSet(event) {
      this.selectedTopicSetId = event.target.value;
      this.loadTopicSet();
    },
    resetState() {
      this.summary = [];
      this.loadingSummary = false;
    },
    async loadTopicSet() {
      this.resetState();
      this.loadingSummary = true;
      const response = await fetch(this.summaryUrl, { credentials: 'same-origin' });
      this.summary = await response.json();
      this.loadingSummary = false;
    }
  }
};
</script>
