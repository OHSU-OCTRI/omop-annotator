<template>
  <div class="person-summary card">
    <div class="card-body py-2">
      <ul class="list-group list-group-horizontal-md mb-0 text-">
        <li data-field="id" class="list-group-item py-0 border-0">
          <label class="text-muted fw-light">Person ID:</label>
          <span class="fw-bolder">{{ person.id }}</span>
        </li>
        <li data-field="dob" class="list-group-item py-0 border-0">
          <label class="text-muted fw-light">DOB:</label> {{ person.birthDatetime }}
        </li>
        <li
          data-field="age"
          :title="`Age as of ${this.person.ageCalculationDate}`"
          class="list-group-item py-0 border-0"
        >
          <label class="text-muted fw-light">Age:</label> {{ person.age }}
        </li>
        <li data-field="gender" class="list-group-item py-0 border-0">
          <label class="text-muted fw-light">Gender:</label> {{ person.gender }}
        </li>
        <li data-field="race" class="list-group-item py-0 border-0">
          <label class="text-muted fw-light">Race:</label> {{ person.race }}
        </li>
        <li data-field="ethnicity" class="list-group-item py-0 border-0">
          <label class="text-muted fw-light">Ethnicity:</label> {{ person.ethnicity }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    person: {
      type: Object,
      required: true
    }
  }
};
</script>
