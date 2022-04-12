<template>
  <PersonSummary :context-path="contextPath" :person-id="personId" />

  <div class="accordion" id="person_data">
    <div class="accordion-item">
      <h2 class="accordion-header" id="visit_heading">
        <button
          class="accordion-button"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapse_visits"
          aria-expanded="true"
          aria-controls="collapse_visits"
        >
          Visits
        </button>
      </h2>
      <div
        id="collapse_visits"
        class="accordion-collapse collapse show"
        aria-labelledby="visit_heading"
        data-bs-parent="#person_data"
      >
        <div class="accordion-body">
          <VisitList
            class="mb-4"
            :context-path="contextPath"
            :person-id="personId"
            :show-header="false"
          />
        </div>
      </div>
    </div>

    <div class="accordion-item">
      <h2 id="conditions_heading" class="accordion-header">
        <button
          class="accordion-button collapsed"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapse_conditions"
          aria-expanded="false"
          aria-controls="collapse_conditions"
        >
          Condition Occurrences
        </button>
      </h2>
      <div
        id="collapse_conditions"
        class="accordion-collapse collapse"
        aria-labelledby="conditions_heading"
        data-bs-parent="#person_data"
      >
        <div class="accordion-body">
          <div>
            <ConditionList
              :context-path="contextPath"
              :person-id="personId"
              :show-header="false"
            />
          </div>
        </div>
      </div>
    </div>

    <div class="accordion-item">
      <h2 class="accordion-header" id="observations_heading">
        <button
          class="accordion-button collapsed"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapse_observations"
          aria-expanded="false"
          aria-controls="collapse_observations"
        >
          Observations
        </button>
      </h2>
      <div
        id="collapse_observations"
        class="accordion-collapse collapse"
        aria-labelledby="observations_heading"
        data-bs-parent="#person_data"
      >
        <div class="accordion-body">
          <ObservationList
            :context-path="contextPath"
            :person-id="personId"
            :show-header="true"
          />
        </div>
      </div>
    </div>

    <div class="accordion-item">
      <h2 class="accordion-header" id="procedures_heading">
        <button
          class="accordion-button collapsed"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapse_procedures"
          aria-expanded="false"
          aria-controls="collapse_procedures"
        >
          Procedures
        </button>
      </h2>
      <div
        id="collapse_procedures"
        class="accordion-collapse collapse"
        aria-labelledby="procedures_heading"
        data-bs-parent="#person_data"
      >
        <div class="accordion-body">
          <ProcedureList
            :context-path="contextPath"
            :person-id="personId"
            :show-header="true"
          />
        </div>
      </div>
    </div>

    <div class="accordion-item">
      <h2 class="accordion-header" id="measurements_heading">
        <button
          class="accordion-button collapsed"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapse_measurements"
          aria-expanded="false"
          aria-controls="collapse_measurements"
        >
          Measurements
        </button>
      </h2>
      <div
        id="collapse_measurements"
        class="accordion-collapse collapse"
        aria-labelledby="measurements_heading"
        data-bs-parent="#person_data"
      >
        <div class="accordion-body">
          <MeasurementList
            :context-path="contextPath"
            :person-id="personId"
            :show-header="true"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ConditionList from './ConditionList';
import MeasurementList from './MeasurementList';
import ObservationList from './ObservationList';
import PersonSummary from './PersonSummary';
import ProcedureList from './ProcedureList';
import VisitList from './VisitList';

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
  components: {
    ConditionList,
    MeasurementList,
    ObservationList,
    PersonSummary,
    ProcedureList,
    VisitList
  },
  data() {
    return {
      selectedVisit: null
    };
  }
};
</script>
