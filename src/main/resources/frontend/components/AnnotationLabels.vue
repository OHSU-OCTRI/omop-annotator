<template>
  <div class="annotation-labels">
    <h2>Annotation Labels</h2>
    <a v-if="this.rowToEdit == null" @click="addRow" class="new-entity btn btn-success">
      <i class="fas fa-plus-circle"></i> New
    </a>
    <div id="annotationLabels">
      <table class="table table-striped table-bordered sorted">
        <tr>
          <th>Display Order</th>
          <th>Display Name</th>
          <th>Output Value</th>
          <th>Accent Color</th>
          <th></th>
          <th></th>
        </tr>
        <tr v-if="labels.length == 0">
          <td colspan="6">None</td>
        </tr>
        <tr v-for="(label, index) in labels" :key="index">
          <td>
            <input
              :class="index !== this.rowToEdit ? 'disabled' : ''"
              :readonly="index !== this.rowToEdit"
              :name="`annotationLabels[${index}].displayOrder`"
              v-model="labels[index].displayOrder"
            />
          </td>
          <td>
            <input
              :class="index !== this.rowToEdit ? 'disabled' : ''"
              :readonly="index !== this.rowToEdit"
              :name="`annotationLabels[${index}].displayLabel`"
              v-model="labels[index].displayLabel"
            />
          </td>
          <td>
            <input
              :class="index !== this.rowToEdit ? 'disabled' : ''"
              :readonly="index !== this.rowToEdit"
              :name="`annotationLabels[${index}].outputLabel`"
              v-model="labels[index].outputLabel"
            />
          </td>
          <td>
            <input
              :class="index !== this.rowToEdit ? 'disabled' : ''"
              :readonly="index !== this.rowToEdit"
              :name="`annotationLabels[${index}].accentColor`"
              v-model="labels[index].accentColor"
            />
          </td>
          <td>
            <span
              v-if="this.rowToEdit == null"
              @click="editLabel(index)"
              class="btn btn-link text-primary"
              ><i class="fas fa-pencil-alt"></i> Edit</span
            >
            <span
              v-if="this.rowToEdit !== null && index == this.rowToEdit"
              @click="saveLabel(index)"
              class="btn btn-link text-primary"
              ><i class="fas fa-save"></i> Save</span
            >
          </td>
          <td>
            <span
              @click="deleteLabel(label.displayLabel)"
              class="btn btn-link text-danger"
              ><i class="fas fa-minus-square"></i> Delete</span
            >
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    contextPath: {
      type: String,
      default: null
    },
    schemaId: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      labels: [],
      rowToEdit: null
    };
  },
  mounted() {
    if (!this.isNew) {
      // Specify same-origin credentials for compatibility with older browsers
      fetch(this.url, { credentials: 'same-origin' })
        .then(response => response.json())
        .then(jsonObj => {
          this.labels = jsonObj;
        });
    }
  },
  methods: {
    addRow() {
      this.labels.push({
        displayLabel: '',
        outputLabel: '',
        displayOrder: '',
        accentColor: ''
      });
      this.rowToEdit = this.labels.length - 1;
    },
    deleteLabel(key) {
      this.labels = this.labels.filter(label => label.displayLabel !== key);
    },
    editLabel(index) {
      this.rowToEdit = index;
    },
    saveLabel(index) {
      this.rowToEdit = null;
    }
  },
  computed: {
    isNew() {
      return this.schemaId === null;
    },
    url() {
      return this.contextPath + '/admin/annotation_label/schema/' + this.schemaId;
    }
  }
};
</script>
