<template>
  <div class="tableFixHead">
    <table class="table table-striped table-bordered sorted">
      <thead>
        <tr>
          <th>Visit Type</th>
          <th>Visit Start</th>
          <th>Visit End</th>
          <th>Visit Source</th>
          <th>Admitting Source</th>
          <th>Discharged To</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="loading">
          <td>Loading...</td>
        </tr>
        <tr v-for="visit in visits" :key="visit.id">
          <td>{{ visit.visitType.name }}</td>
          <td>{{ visit.visitStart }}</td>
          <td>{{ visit.visitEnd }}</td>
          <td>{{ visit.visitSource }}</td>
          <td>{{ visit.admittingSource }}</td>
          <td>{{ visit.dischargedTo }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    contextPath: {
      type: String,
      default: null
    },
    personId: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      visits: [],
      loading: true
    };
  },
  mounted() {
    // Specify same-origin credentials for compatibility with older browsers
    fetch(this.url, { credentials: 'same-origin' })
      .then(response => response.json())
      .then(jsonObj => {
        this.visits = jsonObj;
        this.loading = false;
      });
  },
  computed: {
    url() {
      return `${this.contextPath}/data/person/summary/${this.personId}/visits`;
    }
  }
};
</script>
