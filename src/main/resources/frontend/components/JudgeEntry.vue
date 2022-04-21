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
export default {
  props: {
    contextPath: {
      type: String,
      default: ''
    },
    token: {
      type: String
    },
    poolEntryId: {
      type: Number,
      default: 1
    }
  },
  components: { JudgeButton },
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
    const response = await fetch(this.judgmentUrl, { credentials: 'same-origin' });
    this.judgment = await response.json();
    await this.$nextTick();
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
      this.save();
    },
    async save() {
      const res = await fetch(this.saveUrl, {
        method: 'post',
        credentials: 'same-origin',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json',
          'X-CSRF-TOKEN': this.token
        },
        body: JSON.stringify(this.judgment)
      });
      // On the initial save this will provide us with an id for future updates.
      this.judgment = await res.json();
    }
  }
};
</script>
