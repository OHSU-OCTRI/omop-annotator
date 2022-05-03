<template>
  <button
    type="button"
    :class="{ btn: true, 'annotation-label': true, active: isActive }"
    :style="{
      'background-color': this.label.accentColor,
      color: this.fontColor
    }"
    @click="$emit('select-label', label.id)"
  >
    {{ label.displayLabel }}
  </button>
</template>

<script>
import { isDark } from '../utils/color';
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
    isActive() {
      return this.selected === true;
    },
    fontColor() {
      let color = this.label.accentColor;
      if (color && isDark(color)) {
        return 'white';
      }
      return 'black';
    }
  }
};
</script>
