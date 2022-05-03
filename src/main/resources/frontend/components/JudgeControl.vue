<template>
  <div class="judgment">
    <div class="btn-group btn-group-sm" role="group">
      <JudgeButton
        v-for="(label, index) in sortedLabels"
        :label="label"
        :selected="label.id === this.selected"
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
    labels: Array,
    selectedId: Number
  },
  components: { JudgeButton },
  data() {
    return {
      annotationLabels: this.labels,
      selected: this.selectedId
    };
  },
  computed: {
    sortedLabels() {
      const labels = this.annotationLabels;
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
      this.selected = id;
    }
  }
};
</script>
