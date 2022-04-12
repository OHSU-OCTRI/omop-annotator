<template>
  <PersonSummary :context-path="contextPath" :person-id="personId" />

  <VisitList
    class="mb-4"
    :context-path="contextPath"
    :person-id="personId"
    :show-header="false"
  />

  <ul class="nav nav-tabs mb-3" role="tablist">
    <li class="nav-item" role="presentation">
      <button
        id="condition_occurrences_tab"
        class="nav-link active"
        type="button"
        role="tab"
        data-bs-toggle="tab"
        data-bs-target="#condition_occurrences"
        aria-controls="condition_occurrences"
        aria-selected="true"
      >
        Condition Occurrences
      </button>
    </li>
    <li class="nav-item" role="presentation">
      <button
        id="observations_tab"
        class="nav-link"
        type="button"
        role="tab"
        data-bs-toggle="tab"
        data-bs-target="#observations"
        aria-controls="observations"
        aria-selected="false"
      >
        Observations
      </button>
    </li>
    <li class="nav-item" role="presentation">
      <button
        id="procedures_tab"
        class="nav-link"
        type="button"
        role="tab"
        data-bs-toggle="tab"
        data-bs-target="#procedures"
        aria-controls="procedures"
        aria-selected="false"
      >
        Procedures
      </button>
    </li>
    <li class="nav-item" role="presentation">
      <button
        id="measurements_tab"
        class="nav-link"
        type="button"
        role="tab"
        data-bs-toggle="tab"
        data-bs-target="#measurements"
        aria-controls="measurements"
        aria-selected="false"
      >
        Measurements
      </button>
    </li>
  </ul>
  <div class="tab-content">
    <div
      id="condition_occurrences"
      class="tab-pane fade active show"
      role="tabpanel"
      aria-labelledby="condition_occurrences_tab"
    >
      <ConditionList
        :context-path="contextPath"
        :person-id="personId"
        :show-header="false"
      />
    </div>
    <div
      id="observations"
      class="tab-pane fade"
      role="tabpanel"
      aria-labelledby="observations_tab"
    >
      <ObservationList
        :context-path="contextPath"
        :person-id="personId"
        :show-header="false"
      />
    </div>
    <div
      id="procedures"
      class="tab-pane fade"
      role="tabpanel"
      aria-labelledby="procedures_tab"
    >
      <ProcedureList
        :context-path="contextPath"
        :person-id="personId"
        :show-header="false"
      />
    </div>
    <div
      id="measurements"
      class="tab-pane fade"
      role="tabpanel"
      aria-labelledby="measurements_tab"
    >
      <MeasurementList
        :context-path="contextPath"
        :person-id="personId"
        :show-header="false"
      />
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
