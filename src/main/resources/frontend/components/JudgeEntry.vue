<template>
  <div class="judgment">
    <div class="btn-group btn-group-sm" role="group" :aria-label="title" :title="title">
      <JudgeButton
        v-for="(label, index) in sortedLabels"
        :label="label"
        :selected="label.id === this.judgment.annotationLabelId"
        :key="index"
        @select-label="selectLabel"
      ></JudgeButton>
    </div>
  </div>
</template>

<script>
import JudgeButton from './JudgeButton.vue';
import AnnotatorApi from '../utils/annotator-api';

import { contextPath, csrfToken, csrfHeader } from '../utils/injection-keys';

export default {
  props: {
    poolEntryId: {
      type: Number,
      default: 1
    }
  },
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
  components: { JudgeButton },
  emits: ['judgment-saved'],
  data() {
    return {
      annotatorApi: null,
      // JudgmentDTO
      judgment: {
        id: null,
        userId: null,
        poolEntryId: this.poolEntryId,
        annotationLabelId: null,
        annotationLabels: []
      }
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
    await this.loadJudgment();
  },
  computed: {
    title() {
      return `Judge Entry ${this.poolEntryId}`;
    },
    sortedLabels() {
      const labels = this.judgment.annotationLabels;
      function compareLabel(a, b) {
        if (a.displayOrder < b.displayOrder) return -1;
        if (a.displayOrder > b.displayOrder) return 1;
        return 0;
      }
      return [...labels].sort(compareLabel);
    }
  },
  methods: {
    selectLabel(id) {
      this.judgment.annotationLabelId = id;
      this.saveJudgment();
    },
    async loadJudgment() {
      this.judgment = await this.annotatorApi.loadJudgment(this.poolEntryId);
      await this.$nextTick();
    },
    async saveJudgment() {
      this.judgment = await this.annotatorApi.saveJudgment(this.judgment);
      this.$emit('judgment-saved', this.judgment);
    }
  },
  watch: {
    poolEntryId() {
      this.loadJudgment();
    }
  }
};
</script>
