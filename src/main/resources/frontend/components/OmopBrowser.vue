<template>
  <PersonSummary :person="person" />
  <h2>Visits</h2>
  <div class="d-flex justify-content-center" v-if="visitsLoading">
    <LoadingSpinner />
  </div>
  <div v-else>
    <VisitList
      class="mb-4"
      :visits="visits"
      :show-header="false"
      :selected-visit-id="selectedVisitId"
      @visit-selected="setSelectedVisit"
    />
    <div class="visit-data card">
      <div class="card-header">Visit Data ({{ selectedVisitId }})</div>
      <div class="card-body">
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
              ref="conditionTab"
            >
              Condition Occurrences
              <span v-if="selectedVisitId"> ({{ conditions.length }})</span>
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
              <span v-if="selectedVisitId"> ({{ observations.length }})</span>
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
              <span v-if="selectedVisitId"> ({{ procedures.length }})</span>
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
              <span v-if="selectedVisitId"> ({{ measurements.length }})</span>
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
            <PlaceholderMessage
              v-if="noVisitSelected"
              message="Select a visit to see related conditions."
            />
            <div class="d-flex justify-content-center" v-else-if="loadingVisitData">
              <LoadingSpinner />
            </div>
            <ConditionList v-else :conditions="conditions" :show-header="false" />
          </div>
          <div
            id="observations"
            class="tab-pane fade"
            role="tabpanel"
            aria-labelledby="observations_tab"
          >
            <PlaceholderMessage
              v-if="noVisitSelected"
              message="Select a visit to see related observations."
            />
            <div class="d-flex justify-content-center" v-else-if="loadingVisitData">
              <LoadingSpinner />
            </div>
            <ObservationList v-else :observations="observations" :show-header="false" />
          </div>
          <div
            id="procedures"
            class="tab-pane fade"
            role="tabpanel"
            aria-labelledby="procedures_tab"
          >
            <PlaceholderMessage
              v-if="noVisitSelected"
              message="Select a visit to see related procedures."
            />
            <div class="d-flex justify-content-center" v-else-if="loadingVisitData">
              <LoadingSpinner />
            </div>
            <ProcedureList v-else :procedures="procedures" :show-header="false" />
          </div>
          <div
            id="measurements"
            class="tab-pane fade"
            role="tabpanel"
            aria-labelledby="measurements_tab"
          >
            <PlaceholderMessage
              v-if="noVisitSelected"
              message="Select a visit to see related measurements."
            />
            <div class="d-flex justify-content-center" v-else-if="loadingVisitData">
              <LoadingSpinner />
            </div>
            <MeasurementList v-else :measurements="measurements" :show-header="false" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ConditionList from './ConditionList';
import LoadingSpinner from './LoadingSpinner.vue';
import MeasurementList from './MeasurementList';
import ObservationList from './ObservationList';
import PersonSummary from './PersonSummary';
import PlaceholderMessage from './PlaceholderMessage';
import ProcedureList from './ProcedureList';
import VisitList from './VisitList';

import OmopApi from '../utils/omop-api';

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
    PlaceholderMessage,
    ProcedureList,
    VisitList,
    LoadingSpinner
  },
  data() {
    return {
      omopApi: null,
      person: {},
      visits: [],
      visitsLoading: true,
      selectedVisitId: null,
      conditions: [],
      observations: [],
      procedures: [],
      measurements: [],
      loadingVisitData: false
    };
  },
  async mounted() {
    if (this.omopApi === null) {
      this.omopApi = new OmopApi(this.contextPath);
    }
    await this.loadPerson();
  },
  methods: {
    resetState() {
      this.person = {};
      this.visits = [];
      this.visitsLoading = true;
      this.selectedVisitId = null;
      this.conditions = [];
      this.observations = [];
      this.procedures = [];
      this.measurements = [];
      this.loadingVisitData = false;
    },

    resetTabs() {
      if (window.bootstrap && window.bootstrap.Tab && this.$refs.conditionTab) {
        window.bootstrap.Tab.getOrCreateInstance(this.$refs.conditionTab).show();
      }
    },

    async loadPerson() {
      const { omopApi } = this;
      this.resetState();
      this.resetTabs();

      const [person, visits] = await Promise.all([
        omopApi.getPerson(this.personId),
        omopApi.getVisitsForPerson(this.personId)
      ]);

      this.person = person;
      this.visits = visits;
      this.visitsLoading = false;
    },

    async setSelectedVisit(visitId) {
      if (visitId === this.selectedVisitId) {
        return;
      }

      const { omopApi } = this;
      this.selectedVisitId = visitId;
      this.loadingVisitData = true;
      this.resetTabs();

      const [conditions, observations, procedures, measurements] = await Promise.all([
        omopApi.getConditionsForPersonAndVisit(this.personId, visitId),
        omopApi.getObservationsForPersonAndVisit(this.personId, visitId),
        omopApi.getProceduresForPersonAndVisit(this.personId, visitId),
        omopApi.getMeasurementsForPersonAndVisit(this.personId, visitId)
      ]);

      this.conditions = conditions;
      this.observations = observations;
      this.procedures = procedures;
      this.measurements = measurements;
      this.loadingVisitData = false;
    }
  },
  computed: {
    visitSelected() {
      return this.selectedVisitId !== null;
    },

    noVisitSelected() {
      return !this.visitSelected;
    }
  },
  watch: {
    personId() {
      this.$nextTick(this.loadPerson);
    }
  }
};
</script>
