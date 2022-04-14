<template>
  <div class="d-flex justify-content-center text-muted">
    <span>{{ message }}</span>
  </div>
</template>

<script>
export default {
  props: {
    message: {
      type: String,
      required: true
    }
  }
};
</script>
