<template>
  <a
    :href="modalSelector"
    role="button"
    @click.prevent="editPin()"
    :title="infoMessage"
    :class="isPinned ? 'text-primary' : 'text-unpinned'"
  >
    <i class="fas fa-thumbtack"></i>
    <span class="sr-only">{{ infoMessage }}</span>
  </a>
  <Teleport to="body">
    <BootstrapModal
      :modal-id="modalId"
      :modal-title="modalTitle"
      :show-overlay="true"
      :key="modalId"
    >
      <div class="form-group">
        <label for="comment" class="form-label">Optional Comment</label>
        <textarea
          rows="4"
          class="form-control"
          id="comment"
          name="comment"
          :value="pin.comment"
          ref="comment"
          maxlength="500"
        />
      </div>

      <template v-slot:footerContent>
        <button v-if="isPinned" type="button" class="btn btn-danger" @click="deletePin">
          Delete
        </button>
        <button type="button" class="btn btn-primary" @click="savePin">Save</button>
      </template>
    </BootstrapModal>
  </Teleport>
</template>

<script>
import BootstrapModal from './BootstrapModal';
export default {
  props: {
    pin: {
      type: Object,
      required: true
    }
  },
  components: {
    BootstrapModal
  },
  emits: ['pin-saved', 'pin-deleted'],
  data() {
    return {
      infoMessage: 'Pin the visit and add a comment',
      modalId: `pin-modal-${this.pin.entityId}`
    };
  },
  mounted() {
    const { modalSelector } = this;
    // Prefix global bootstrap with window to make explicit for running command line tests
    if (window.bootstrap && window.bootstrap.Modal) {
      this.modal = new window.bootstrap.Modal(modalSelector);
    } else {
      console.error('Bootstrap JS has not been loaded.');
    }
  },
  unmounted() {
    if (this.modal) {
      this.modal.dispose();
    }
  },
  methods: {
    editPin() {
      this.modal.toggle();
    },
    savePin() {
      let editedPin = this.pin;
      editedPin.comment = this.$refs.comment.value;
      this.$emit('pin-saved', editedPin);
      this.modal.toggle();
    },
    deletePin() {
      this.$emit('pin-deleted', this.pin);
      this.modal.toggle();
    }
  },
  computed: {
    isPinned() {
      return this.pin.id !== null;
    },
    modalSelector() {
      return `#${this.modalId}`;
    },
    modalTitle() {
      return `Visit Id: ${this.pin.entityId}`;
    }
  }
};
</script>
