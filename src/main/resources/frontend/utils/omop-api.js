const defaultFetchOptions = Object.freeze({ credentials: 'same-origin' });

/**
 * A simple wrapper for accessing OMOP endpoints.
 */
export default class OmopApi {
  /**
   * @param {string} contextPath - the application's context path. Used as the prefix for
   *  URLs. Defaults to an empty string.
   * @param {Object} fetchOptions - default options to pass to fetch requests. Defaults to
   *  `{ credentials: 'same-origin' }` for compatibility with older browsers.
   */
  constructor(contextPath = '', fetchOptions = defaultFetchOptions) {
    this.contextPath = contextPath;
    this.fetchOptions = fetchOptions;
  }

  /**
   * URL prefix of OMOP data related to a person entity.
   */
  get personPrefix() {
    return this.contextPath + '/data/person/summary';
  }

  /**
   * Gets the `Person` with the specified ID.
   *
   * @param {number} personId
   * @returns {Promise<Person>}
   */
  async getPerson(personId) {
    const url = `${this.personPrefix}/${personId}`;
    return await this.getJson(url);
  }

  /**
   * Gets all `VisitOccurrence`s for the given person ID.
   *
   * @param {number} personId
   * @returns {Promise<Array<VisitOccurrence>>}
   */
  async getVisitsForPerson(personId) {
    const url = `${this.personPrefix}/${personId}/visits`;
    return await this.getJson(url);
  }

  /**
   * Gets all `ConditionOccurrence`s for the given person ID.
   *
   * @param {number} personId
   * @returns {Promise<Array<ConditionOccurrence>>}
   */
  async getConditionsForPerson(personId) {
    const url = `${this.personPrefix}/${personId}/conditions`;
    return await this.getJson(url);
  }

  /**
   * Gets `ConditionOccurrence`s for the given person ID and visit ID.
   *
   * @param {number} personId
   * @param {number} visitId
   * @returns {Promise<Array<ConditionOccurrence>>}
   */
  async getConditionsForPersonAndVisit(personId, visitId) {
    const url = `${this.personPrefix}/${personId}/visit/${visitId}/conditions`;
    return await this.getJson(url);
  }

  /**
   * Gets all `Observation`s for the given person ID.
   *
   * @param {number} personId
   * @returns {Promise<Array<Observation>>}
   */
  async getObservationsForPerson(personId) {
    const url = `${this.personPrefix}/${personId}/observations`;
    return await this.getJson(url);
  }

  /**
   * Gets all `Observation`s for the given person ID and visit ID.
   *
   * @param {number} personId
   * @param {number} visitId
   * @returns {Promise<Array<Observation>>}
   */
  async getObservationsForPersonAndVisit(personId, visitId) {
    const url = `${this.personPrefix}/${personId}/visit/${visitId}/observations`;
    return await this.getJson(url);
  }

  /**
   * Gets all `ProcedureOccurrence`s for the given person ID.
   *
   * @param {number} personId
   * @returns {Promise<Array<ProcedureOccurrence>>}
   */
  async getProceduresForPerson(personId) {
    const url = `${this.personPrefix}/${personId}/procedures`;
    return await this.getJson(url);
  }

  /**
   * Gets all `ProcedureOccurrence`s for the given person ID and visit ID.
   * @param {number} personId
   * @param {number} visitId
   * @returns {Promise<Array<ProcedureOccurrence>>}
   */
  async getProceduresForPersonAndVisit(personId, visitId) {
    const url = `${this.personPrefix}/${personId}/visit/${visitId}/procedures`;
    return await this.getJson(url);
  }

  /**
   * Gets all `Measurement`s for the given person ID.
   *
   * @param {number} personId
   * @returns {Promise<Array<Measurement>>}
   */
  async getMeasurementsForPerson(personId) {
    const url = `${this.personPrefix}/${personId}/measurements`;
    return await this.getJson(url);
  }

  /**
   * Gets all `Measurement`s for the given person ID and visit ID.
   *
   * @param {number} personId
   * @param {number} visitId
   * @returns {Promise<Array<Measurement>>}
   */
  async getMeasurementsForPersonAndVisit(personId, visitId) {
    const url = `${this.personPrefix}/${personId}/visit/${visitId}/measurements`;
    return await this.getJson(url);
  }

  /**
   * Gets all `Notes`s for the given person ID.
   *
   * @param {number} personId
   * @returns {Promise<Array<Note>>}
   */
  async getNotesForPerson(personId) {
    const url = `${this.personPrefix}/${personId}/notes`;
    return await this.getJson(url);
  }

  /**
   * Gets all `Note`s for the given person ID and visit ID.
   *
   * @param {number} personId
   * @param {number} visitId
   * @returns {Promise<Array<Note>>}
   */
  async getNotesForPersonAndVisit(personId, visitId) {
    const url = `${this.personPrefix}/${personId}/visit/${visitId}/notes`;
    return await this.getJson(url);
  }

  /**
   * Gets all `DrugExposures`s for the given person ID, grouped by drug.
   *
   * @param {number} personId
   * @returns {Promise<Array<Drug>>}
   */
  async getDrugsForPerson(personId) {
    const url = `${this.personPrefix}/${personId}/drugs`;
    const drugs = await this.getJson(url);
    return drugs.reduce(
      (entryMap, e) => entryMap.set(e.drug, [...(entryMap.get(e.drug) || []), e]),
      new Map()
    );
  }

  /**
   * Gets all `DrugExposures`s for the given person ID and visit ID, grouped by drug.
   *
   * @param {number} personId
   * @param {number} visitId
   * @returns {Promise<Array<Drug>>}
   */
  async getDrugsForPersonAndVisit(personId, visitId) {
    const url = `${this.personPrefix}/${personId}/visit/${visitId}/drugs`;
    const drugs = await this.getJson(url);
    return drugs.reduce(
      (entryMap, e) => entryMap.set(e.drug, [...(entryMap.get(e.drug) || []), e]),
      new Map()
    );
  }

  /**
   * Search Person data and return a list of visit ids that match.
   *
   * @param {number} personId
   * @param {string}  entity
   * @param {string} term
   * @returns {Promise<Array<number>>}
   */
  async searchPersonData(personId, entity, term) {
    const url = `${this.personPrefix}/${personId}/visits/filter/${entity}?name=${term}`;
    return await this.getJson(url);
  }

  /**
   * Makes a `fetch` request to the given URL, expecting a JSON response. Returns parsed
   * JSON data.
   *
   * @param {string} url
   * @returns {Promise}
   */
  async getJson(url) {
    const response = await fetch(url, this.fetchOptions);
    if (!response.ok) {
      throw new Error(response.statusText);
    }
    return await response.json();
  }
}
