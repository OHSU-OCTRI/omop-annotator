<template>
  <button
    type="button"
    :class="{ btn: true, 'annotation-label': true, active: isActive }"
    :style="{
      'background-color': this.label.accentColor,
      'border-color': this.borderColor
    }"
    @click="$emit('select-label', label.id)"
  >
    {{ label.displayLabel }}
  </button>
</template>

<script>
export default {
  props: {
    label: {
      type: Object
    },
    selected: {
      type: Boolean
    }
  },
  emits: ['select-label'],
  computed: {
    borderSize() {
      return this.isActive ? 1 : 0.5;
    },
    borderColor() {
      return this.isActive ? 'gray' : 'lightgray';
    },
    isActive() {
      return this.selected === true;
    }
  }
};
</script>
