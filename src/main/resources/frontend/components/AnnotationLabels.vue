<template>
  <div class="annotation-labels">
    <h2>Annotation Labels</h2>
    <a @click="addRow" class="new-entity btn btn-success">
      <i class="fas fa-plus-circle"></i> New
    </a>
    <div id="annotationLabels">
      <table class="table table-striped table-bordered sorted">
        <tbody>
          <tr>
            <th>Display Order</th>
            <th>Display Name</th>
            <th>Output Value</th>
            <th>Accent Color</th>
            <th></th>
          </tr>
          <tr v-if="labels.length == 0">
            <td colspan="6">None</td>
          </tr>
          <tr v-for="(label, index) in labels" :key="index">
            <td>
              <input
                :name="`annotationLabels[${index}].displayOrder`"
                v-model="labels[index].displayOrder"
              />
            </td>
            <td>
              <input
                :name="`annotationLabels[${index}].displayLabel`"
                v-model="labels[index].displayLabel"
                required
              />
              <div class="invalid-feedback">Value must be present</div>
            </td>
            <td>
              <input
                :name="`annotationLabels[${index}].outputLabel`"
                v-model="labels[index].outputLabel"
              />
            </td>
            <td>
              <input
                :name="`annotationLabels[${index}].accentColor`"
                v-model="labels[index].accentColor"
              />
            </td>
            <td>
              <span :name="`delete${index}`"
                @click="deleteLabel(index)"
                class="btn btn-link text-danger"
                ><i class="fas fa-minus-square"></i> Delete</span
              >
            </td>
          </tr>
        </tbody>
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
    },
    deleteLabel(index) {
      this.labels.splice(index, 1);
    },
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
