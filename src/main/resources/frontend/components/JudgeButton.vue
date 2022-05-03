<template>
  <button
    type="button"
    :class="{
      btn: true,
      'annotation-label': true,
      active: isActive,
      light: isLight,
      dark: isDark
    }"
    :style="{
      'background-color': this.label.accentColor
    }"
    @click="$emit('select-label', label.id)"
  >
    {{ label.displayLabel }}
  </button>
</template>

<script>
import { isDark, isLight } from '../utils/color';
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
    isLight() {
      return this.label.accentColor && isLight(this.label.accentColor);
    },
    isDark() {
      return this.label.accentColor && isDark(this.label.accentColor);
    }
  }
};
</script>
