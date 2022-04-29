<template>
  <div class="d-flex w-100 justify-content-between align-items-center">
    <span>{{ entryJudgment.documentId }}</span>
    <template v-if="isJudged">
      <small v-if="showAnnotation" class="fw-light">{{ entryJudgment.annotation }}</small>
      <span v-else :class="iconClasses" aria-label="Judged document"></span>
    </template>
  </div>
</template>

<script>
export default {
  props: {
    entryJudgment: {
      type: Object,
      required: true
    },
    selected: {
      type: Boolean,
      default: false
    },
    showJudgment: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    iconClasses() {
      if (!this.isJudged) {
        return '';
      }

      const classes = [
        // override line-height to align with text
        'lh-base',
        // icon
        'far',
        'fa-check-circle'
      ];

      if (!this.selected) {
        // colored icon when not selected
        classes.push('text-primary');
      }

      return classes.join(' ');
    },

    isJudged() {
      return this.entryJudgment.judgmentId !== null;
    },

    showAnnotation() {
      return this.showJudgment && this.isJudged;
    }
  }
};
</script>
