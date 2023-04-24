<template>
  <div class="row">
    <div class="form-group">
      <label for="merge_pool" class="form-label required-field">Pool to Merge</label>
      <select
        class="form-select"
        id="merge_pool"
        name="merge_pool"
        @change="changeMergePool($event)"
        required
      >
        <option key="none" value="-1">--</option>
        <option v-for="pool in pools" :key="pool.id" :value="pool.id">
          {{ pool.topicSet.name }} - {{ pool.name }}
        </option>
      </select>
    </div>
    <div v-if="hasRelatedPools" class="form-group">
      <label for="destination_pool" class="form-label required-field"
        >Destination Pool</label
      >
      <select
        class="form-select"
        id="destination_pool"
        name="destination_pool"
        @change="changeDestinationPool($event)"
        required
      >
        <option key="none" value="-1">--</option>
        <option v-for="pool in relatedPools" :key="pool.id" :value="pool.id">
          {{ pool.name }}
        </option>
      </select>
    </div>
    <div
      v-if="mergePoolIsSelected && !hasRelatedPools"
      class="alert alert-danger"
      role="alert"
    >
      There are no compatible pools with the same Topic Set and Annotation Schema
    </div>
  </div>
  <button
    v-if="poolsAreSelected && mergeResult == null"
    type="submit"
    class="btn btn-primary mt-2"
    @click="mergePools()"
  >
    Submit
  </button>
  <div v-if="hasMergeResult">
    <div v-if="!mergeResult.successful">
      <div class="alert alert-danger" role="alert">
        Pools cannot be merged. Fix the following errors and try again.
      </div>
      <table class="table">
        <thead>
          <th>Error</th>
        </thead>
        <tbody>
          <tr v-for="(error, index) in mergeResult.errors" :key="index">
            <td>{{ error }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else class="alert alert-success" role="alert">Pools successfully merged!</div>
  </div>
</template>

<script>
import AnnotatorApi from '../utils/annotator-api';
import { contextPath, csrfToken, csrfHeader } from '../utils/injection-keys';
export default {
  inject: {
    [contextPath]: {
      default: ''
    },
    [csrfToken]: {
      default: ''
    },
    [csrfHeader]: {
      default: 'X-CSRF-TOKEN'
    }
  },
  data() {
    return {
      annotatorApi: null,
      pools: [],
      selectedMergePoolId: null,
      selectedDestinationPoolId: null,
      mergeResult: null
    };
  },
  async mounted() {
    if (this.annotatorApi === null) {
      this.annotatorApi = new AnnotatorApi(
        this.contextPath,
        this.csrfHeader,
        this.csrfToken
      );
    }

    this.pools = await this.annotatorApi.getPools();
  },
  computed: {
    mergePoolIsSelected() {
      return this.selectedMergePoolId !== null && this.selectedMergePoolId !== -1;
    },
    destinationPoolIsSelected() {
      return (
        this.selectedDestinationPoolId !== null && this.selectedDestinationPoolId !== -1
      );
    },
    poolsAreSelected() {
      return this.mergePoolIsSelected && this.destinationPoolIsSelected;
    },
    selectedMergePool() {
      return this.mergePoolIsSelected
        ? this.pools.find(p => p.id === this.selectedMergePoolId)
        : null;
    },
    relatedPools() {
      return this.pools.filter(
        p =>
          p.id !== this.selectedMergePoolId &&
          p.topicSet.id === this.selectedMergePool.topicSet.id &&
          p.annotationSchema.id === this.selectedMergePool.annotationSchema.id
      );
    },
    hasRelatedPools() {
      return this.mergePoolIsSelected && this.relatedPools.length > 0;
    },
    hasMergeResult() {
      return this.mergeResult !== null;
    }
  },
  methods: {
    changeMergePool(event) {
      this.selectedMergePoolId = Number(event.target.value);
      // TODO: This hides the button but the second dropdown still appears and is selected
      this.selectedDestinationPoolId = null;
      this.mergeResult = null;
    },
    changeDestinationPool(event) {
      this.selectedDestinationPoolId = Number(event.target.value);
      this.mergeResult = null;
    },
    async mergePools() {
      this.mergeResult = await this.annotatorApi.mergePools(
        this.selectedMergePoolId,
        this.selectedDestinationPoolId
      );
      if (this.mergeResult.successful) {
        this.selectedMergePoolId = null;
        this.selectedDestinationPoolId = null;
        // Get the pools again
        this.pools = await this.annotatorApi.getPools();
      }
    }
  }
};
</script>
