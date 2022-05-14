<template>
  <div :class="classes">
    <span class="ms-2"></span>
    <span
      class="spinner-border spinner-border-sm text-secondary ms-auto"
      role="status"
      aria-hidden="true"
    >
    </span>
    <span class="ms-2">Loading...</span>
  </div>
</template>
<script>
export default {
  props: {
    inline: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    classes() {
      return this.inline ? 'd-inline' : '';
    }
  }
};
</script>
