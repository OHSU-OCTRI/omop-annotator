<template>
  <div class="visit-list tableFixHead">
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
        <tr v-for="(visit, index) in visits" :key="visit.id">
          <td :data-field="`visitType${index}`">{{ visit.visitType.name }}</td>
          <td :data-field="`visitStart${index}`">{{ visit.visitStart }}</td>
          <td :data-field="`visitEnd${index}`">{{ visit.visitEnd }}</td>
          <td :data-field="`visitSource${index}`">{{ visit.visitSource }}</td>
          <td :data-field="`admittingSource${index}`">{{ visit.admittingSource }}</td>
          <td :data-field="`dischargedTo${index}`">{{ visit.dischargedTo }}</td>
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
      default: ''
    },
    personId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      visits: [],
      loading: true
    };
  },
  mounted() {
    if (this.visits.length === 0) {
      fetch(this.url, { credentials: 'same-origin' })
        .then(response => response.json())
        .then(jsonObj => {
          this.visits = jsonObj;
          this.loading = false;
        });
    }
  },
  computed: {
    url() {
      return `${this.contextPath}/data/person/summary/${this.personId}/visits`;
    }
  }
};
</script>
