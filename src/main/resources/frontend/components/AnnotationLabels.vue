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
              <input @change="setValidity(index, 'displayOrder', $event)" class="form-control"
                type="number"
                :name="`annotationLabels[${index}].displayOrder`"
                v-model="labels[index].displayOrder"
                required
              />
              <div class="invalid-feedback">Value must be present and unique</div>
            </td>
            <td>
              <input @change="setValidity(index, 'displayLabel', $event)" class="form-control"
                :name="`annotationLabels[${index}].displayLabel`"
                v-model="labels[index].displayLabel"
                required
              />
            </td>
            <td>
              <input @change="setValidity(index, 'outputLabel', $event)" class="form-control"
                :name="`annotationLabels[${index}].outputLabel`"
                v-model="labels[index].outputLabel"
                required
              />
              <div class="invalid-feedback">Value must be present and unique</div>
            </td>
            <td>
              <input @change="setValidity(index, 'accentColor', $event)" class="form-control form-control-color w-100"
                type="color"
                :name="`annotationLabels[${index}].accentColor`"
                v-model="labels[index].accentColor"
              />
              <div class="invalid-feedback">Value must be unique</div>
            </td>
            <td>
              <span
                :name="`delete${index}`"
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
      labels: []
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
      // Assign default color of black or a random color if black is in use
      let color = '#000000';
      if (this.labels.filter(label => label.accentColor === color).length > 0) {
        color = '#' + Math.floor(Math.random()*16777215).toString(16);
      }
      this.labels.push({
        displayLabel: '',
        outputLabel: '',
        displayOrder: '',
        accentColor: color
      });
    },
    deleteLabel(index) {
      this.labels.splice(index, 1);
    },
    setValidity(index, propName, event) {
      const val = this.labels[index][propName];
      const matches = this.labels.filter(label => label[propName] === val);
      if (matches.length > 1) {
        event.target.classList.add('is-invalid');
        event.target.setCustomValidity('Invalid');
      } else {
        event.target.classList.remove('is-invalid');
        event.target.setCustomValidity('');
      }
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
