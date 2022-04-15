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
<style>
button.annotation-label {
  filter: opacity(80%);
  font-size: 0.8rem;
}
button.annotation-label:hover,
button.annotation-label:focus,
button.annotation-label.active {
  filter: opacity(100%);
}
button.annotation-label:focus {
  filter: brightness(95%);
}
button.annotation-label.active {
  filter: saturate(120%);
}
</style>