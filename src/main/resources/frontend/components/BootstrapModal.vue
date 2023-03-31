<template>
  <div
    :id="modalId"
    class="modal fade"
    tabindex="-1"
    role="dialog"
    :aria-labelledby="titleId"
    aria-hidden="true"
    :data-bs-backdrop="backdropType"
    :data-bs-keyboard="keyboardEnabled"
  >
    <div class="modal-dialog" :class="sizeClass" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <slot name="headerContent"></slot>
          <template v-if="!hasHeaderContent">
            <h5 class="modal-title" :id="titleId">{{ modalTitle }}</h5>
            <button
              v-if="allowClose"
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </template>
        </div>

        <div class="modal-body">
          <slot></slot>
        </div>

        <div class="modal-footer" v-if="allowClose">
          <slot name="footerContent"></slot>
          <button
            type="button"
            class="btn btn-primary"
            data-bs-dismiss="modal"
            v-if="!hasFooterContent"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/**
 * Renders a Bootstrap modal. To show and hide the modal, use the modal methods as shown in
 * the Bootstrap documentation. If the component isn't working as expected, make sure Bootstrap's
 * JavaScript has been loaded.
 *
 * **Slots**
 *
 * default: Elements to use as the modal's contents. Rendered in the `modal-body` div.
 * headerContent: Use to customize the content of the `.modal-header` div. Overrides the
 *   `modalTitle` prop. If not present, an `<h5>` containing the `modalTitle` prop and
 *   a close button will be rendered.
 * footerContent: Use to customize the content of the `.modal-footer` div, for example if
 *   you want a confirm button. Suppressed if `allowClose = false`. If not present and
 *   `allowClose = true` a single close button will be rendered.
 *
 * @param {string} modalId - ID of the modal's main `<div>`. Required.
 * @param {string} modalTitle - text to display in the modal's title. For full control of
 *   the markup in the modal header, use the `headerContent` slot.
 * @param {string} modalSize - one of `sm`, `lg`, `xl`. See "Optional Sizes" in Bootstrap
 *   documentation.
 * @param {boolean} showOverlay - whether to display an overlay behind the modal. Optional,
 *   defaults to `false`.
 * @param {boolean} allowClose - whether the user may close the modal. When false, the buttons
 *   that close the modal will be hidden, and the keyboard shortcut will be disabled. See
 *   "Static Backdrop" in Bootstrap documentation. Overrides the `showOverlay` prop.
 *
 * @see https://getbootstrap.com/docs/5.2/components/modal/
 */
export default {
  props: {
    modalId: {
      type: String,
      required: true
    },
    modalTitle: {
      type: String
    },
    modalSize: {
      type: String,
      validator(value) {
        return ['sm', 'lg', 'xl'].indexOf(value) !== -1;
      }
    },
    showOverlay: {
      type: Boolean,
      default: false
    },
    allowClose: {
      type: Boolean,
      default: true
    }
  },

  computed: {
    titleId() {
      return `${this.modalId}Label`;
    },

    hasHeaderContent() {
      return Boolean(this.$slots.headerContent);
    },

    hasFooterContent() {
      return Boolean(this.$slots.footerContent);
    },

    sizeClass() {
      const { modalSize } = this;
      if (modalSize) {
        return `modal-${modalSize}`;
      }

      return '';
    },

    backdropType() {
      const { allowClose, showOverlay } = this;
      if (!allowClose) {
        return 'static';
      } else {
        return showOverlay.toString();
      }
    },

    keyboardEnabled() {
      return this.allowClose.toString();
    }
  }
};
</script>
