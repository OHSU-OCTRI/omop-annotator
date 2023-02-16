<template>
  <div class="mb-4">
    <JudgeEntry
      :pool-entry-id="poolEntryId"
      @judgment-saved="handleJudgment"
      class="mb-2"
    />
    <PersonSummary :person="person" />
  </div>
  <h2 class="fs-4">Visits</h2>
  <div class="d-flex justify-content-center" v-if="visitsLoading">
    <LoadingSpinner />
  </div>
  <div v-else>
    <VisitList
      class="mb-4"
      :visits="visits"
      :show-header="false"
      :person-id="personId"
      :selected-visit-id="selectedVisitId"
      @visit-selected="setSelectedVisit"
    />
    <div class="visit-data card">
      <div class="card-header">
        Visit Data<span v-if="selectedVisitId"> (id: {{ selectedVisitId }})</span>
      </div>
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
              Conditions
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
              <span v-if="selectedVisitId"> ({{ measurements.size }})</span>
            </button>
          </li>
          <li class="nav-item" role="presentation">
            <button
              id="notes_tab"
              class="nav-link"
              type="button"
              role="tab"
              data-bs-toggle="tab"
              data-bs-target="#notes"
              aria-controls="notes"
              aria-selected="false"
            >
              Notes
              <span v-if="selectedVisitId"> ({{ notes.length }})</span>
            </button>
          </li>
          <li class="nav-item" role="presentation">
            <button
              id="drugs_tab"
              class="nav-link"
              type="button"
              role="tab"
              data-bs-toggle="tab"
              data-bs-target="#drugs"
              aria-controls="drugs"
              aria-selected="false"
            >
              Medications
              <span v-if="selectedVisitId"> ({{ drugs.size }})</span>
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
            <VisitRelatedList
              v-else
              :items="conditions"
              :configuration="getConfigurationForEntity('Condition')"
              itemType="Condition"
              :show-header="false"
            />
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
            <VisitRelatedList
              v-else
              :items="observations"
              :configuration="getConfigurationForEntity('Observation')"
              itemType="Observation"
              :show-header="false"
            />
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
            <VisitRelatedList
              v-else
              :items="procedures"
              :configuration="getConfigurationForEntity('Procedure')"
              itemType="Procedure"
              :show-header="false"
            />
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
          <div
            id="notes"
            class="tab-pane fade"
            role="tabpanel"
            aria-labelledby="notes_tab"
          >
            <PlaceholderMessage
              v-if="noVisitSelected"
              message="Select a visit to see related notes."
            />
            <div class="d-flex justify-content-center" v-else-if="loadingVisitData">
              <LoadingSpinner />
            </div>
            <NoteList
              v-else
              :notes="notes"
              :configuration="getConfigurationForEntity('Note')"
              :show-header="false"
            />
          </div>
          <div
            id="drugs"
            class="tab-pane fade"
            role="tabpanel"
            aria-labelledby="drugs_tab"
          >
            <PlaceholderMessage
              v-if="noVisitSelected"
              message="Select a visit to see related medications."
            />
            <div class="d-flex justify-content-center" v-else-if="loadingVisitData">
              <LoadingSpinner />
            </div>
            <DrugList v-else :drugs="drugs" :show-header="false" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VisitRelatedList from './VisitRelatedList';
import DrugList from './DrugList';
import JudgeEntry from './JudgeEntry';
import LoadingSpinner from './LoadingSpinner.vue';
import MeasurementList from './MeasurementList';
import NoteList from './NoteList';
import PersonSummary from './PersonSummary';
import PlaceholderMessage from './PlaceholderMessage';
import VisitList from './VisitList';

import { contextPath } from '../utils/injection-keys';
import OmopApi from '../utils/omop-api';

export default {
  props: {
    personId: {
      type: Number,
      required: true
    },
    poolEntryId: {
      type: Number,
      required: true
    }
  },
  inject: {
    [contextPath]: {
      default: ''
    }
  },
  components: {
    VisitRelatedList,
    DrugList,
    MeasurementList,
    NoteList,
    PersonSummary,
    PlaceholderMessage,
    VisitList,
    LoadingSpinner,
    JudgeEntry
  },
  emits: ['judgment-saved'],
  data() {
    return {
      configuration: null,
      omopApi: null,
      person: {},
      visits: [],
      visitsLoading: true,
      selectedVisitId: null,
      conditions: [],
      observations: [],
      procedures: [],
      measurements: {},
      notes: [],
      drugs: {},
      loadingVisitData: false
    };
  },
  async mounted() {
    if (this.omopApi === null) {
      this.omopApi = new OmopApi(this.contextPath);
    }
    if (this.configuration === null) {
      await this.getDisplayConfig();
    }
    await this.loadPerson();
  },
  methods: {
    handleJudgment(...data) {
      this.$emit('judgment-saved', ...data);
    },
    resetState() {
      this.person = {};
      this.visits = [];
      this.visitsLoading = true;
      this.selectedVisitId = null;
      this.conditions = [];
      this.observations = [];
      this.procedures = [];
      this.measurements = {};
      this.notes = [];
      this.drugs = {};
      this.loadingVisitData = false;
    },

    resetTabs() {
      if (window.bootstrap && window.bootstrap.Tab && this.$refs.conditionTab) {
        window.bootstrap.Tab.getOrCreateInstance(this.$refs.conditionTab).show();
      }
    },

    async getDisplayConfig() {
      const res = await fetch(`${this.contextPath}/display/`);
      const finalRes = await res.json();
      this.configuration = finalRes;
    },

    getConfigurationForEntity(entityName) {
      return this.configuration.filter(f => f.entityName === entityName);
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

      const [conditions, observations, procedures, measurements, notes, drugs] =
        await Promise.all([
          omopApi.getConditionsForPersonAndVisit(this.personId, visitId),
          omopApi.getObservationsForPersonAndVisit(this.personId, visitId),
          omopApi.getProceduresForPersonAndVisit(this.personId, visitId),
          omopApi.getMeasurementsForPersonAndVisit(this.personId, visitId),
          omopApi.getNotesForPersonAndVisit(this.personId, visitId),
          omopApi.getDrugsForPersonAndVisit(this.personId, visitId)
        ]);

      this.conditions = conditions;
      this.observations = observations;
      this.procedures = procedures;
      this.measurements = measurements;
      this.notes = notes;
      this.drugs = drugs;
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
