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
  <div class="row">
    <div class="form-group">
      <label for="pool" class="form-label required-field">Pool</label>
      <select
        class="form-select"
        id="pool"
        name="pool"
        @change="loadPool($event)"
        required
      >
        <option key="none" value="">--</option>
        <option v-for="pool in pools" :key="pool.id" :value="pool.id">
          {{ pool.name }}
        </option>
      </select>
    </div>
  </div>
  <div v-if="selectedPool">
    <h2>Judgment Progress Dashboard</h2>
    <div class="d-flex justify-content-center" v-if="loadingSummary">
      <LoadingSpinner />
    </div>
    <PoolSummary v-else :summary="summary" />
  </div>
</template>

<script>
import PoolSummary from './PoolSummary';
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
    PoolSummary,
    LoadingSpinner
  },
  data() {
    return {
      annotatorApi: null,
      topicSets: [],
      selectedTopicSetId: null,
      pools: [],
      selectedPool: null,
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

    this.pools = await this.annotatorApi.getPoolsForTopicSet(this.selectedTopicSetId);
  },
  methods: {
    async changeTopicSet(event) {
      this.selectedTopicSetId = event.target.value;
      this.resetState();
      this.pools = await this.annotatorApi.getPoolsForTopicSet(this.selectedTopicSetId);
    },
    resetState() {
      this.selectedPool = null;
      this.summary = [];
      this.loadingSummary = true;
    },
    async loadPool(event) {
      this.resetState();
      this.selectedPool = event.target.value;
      if (this.selectedPool) {
        this.summary = await this.annotatorApi.getPoolSummary(this.selectedPool);
      }
      this.loadingSummary = false;
    }
  }
};
</script>
