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
import AnnotatorApi from '../utils/annotator-api';
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
      annotatorApi: null,
      topicSets: [],
      selectedTopicSetId: null,
      summary: [],
      loadingSummary: false
    };
  },
  async mounted() {
    if (this.annotatorApi === null) {
      this.annotatorApi = new AnnotatorApi(this.contextPath);
    }

    if (!this.topicSets.length > 0) {
      this.topicSets = await this.annotatorApi.getTopicSets();
    }

    if (this.topicSets.length > 0 && !this.selectedTopicSet) {
      this.selectedTopicSetId = this.topicSets[0].id;
    }

    await this.loadTopicSet();
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
      this.summary = await this.annotatorApi.getTopicSetSummary(this.selectedTopicSetId);
      this.loadingSummary = false;
    }
  }
};
</script>
