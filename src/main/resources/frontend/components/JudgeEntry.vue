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
    await this.loadJudgment();
  },
  computed: {
    judgmentUrl() {
      return `${this.contextPath}/data/judgment/pool_entry/${this.poolEntryId}`;
    },
    saveUrl() {
      return `${this.contextPath}/data/judgment/save_judgment`;
    },
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
      const response = await fetch(this.judgmentUrl, { credentials: 'same-origin' });
      this.judgment = await response.json();
      await this.$nextTick();
    },
    async saveJudgment() {
      const { csrfHeader } = this;
      const res = await fetch(this.saveUrl, {
        method: 'post',
        credentials: 'same-origin',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json',
          [csrfHeader]: this.csrfToken
        },
        body: JSON.stringify(this.judgment)
      });
      // On the initial save this will provide us with an id for future updates.
      this.judgment = await res.json();
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
