import Api from './api.js';
/**
 * A simple wrapper for accessing Annotator endpoints.
 */
export default class AnnotatorApi extends Api {
  /**
   * @param {string} contextPath - the application's context path. Used as the prefix for
   *  URLs. Defaults to an empty string.
   * @param {string} csrfHeader - only needed if calling POST actions to save/delete
   * @param {string} csrfToken - only needed if calling POST actions to save/delete
   * @param {Object} fetchOptions - default options to pass to fetch requests. Defaults to
   *  `{ credentials: 'same-origin' }` for compatibility with older browsers.
   */
  constructor(contextPath, csrfHeader = null, csrfToken = null, fetchOptions) {
    super(contextPath, fetchOptions);
    this.csrfHeader = csrfHeader;
    this.csrfToken = csrfToken;
  }

  /**
   * Get the annotation labels for the given schema
   * @param {Long} schemaId
   * @returns
   */
  async getLabelsForAnnotationSchema(schemaId) {
    const url = `${this.contextPath}/admin/api/annotation_label/schema/${schemaId}`;
    return await this.getJson(url);
  }

  /**
   * Get all topic sets
   * @returns
   */
  async getTopicSets() {
    const url = `${this.contextPath}/admin/api/topic_sets`;
    return await this.getJson(url);
  }

  /**
   * Get the summary for the given topic set
   * @param {Long} topicSetId
   * @returns
   */
  async getTopicSetSummary(topicSetId) {
    const url = `${this.contextPath}/admin/api/topic_set/${topicSetId}/summary`;
    return await this.getJson(url);
  }

  /**
   * Get the judgments for the pool and topic for the authenticated user
   * @param {Long} poolId
   * @param {Long} topicId
   * @returns
   */
  async getPoolEntryJudgments(poolId, topicId) {
    const url = `${this.contextPath}/data/api/pool/${poolId}/topic/${topicId}/pool_entry_judgments`;
    return await this.getJson(url);
  }

  /**
   * Get display configuration for OMOP tables
   */
  async getDisplayConfig() {
    const url = `${this.contextPath}/data/api/omop_display_configuration`;
    return await this.getJson(url);
  }

  /**
   * Load the user's judgment for the pool entry
   * @param {Long} poolEntryId
   */
  async loadJudgment(poolEntryId) {
    const url = `${this.contextPath}/data/api/judgment/pool_entry/${poolEntryId}`;
    return await this.getJson(url);
  }

  /**
   * Save a judgment
   * @param {JudgmentDTO} judgment
   * @returns
   */
  async saveJudgment(judgment) {
    const { csrfHeader } = this;
    const res = await fetch(`${this.contextPath}/data/api/judgment/save_judgment`, {
      method: 'post',
      credentials: 'same-origin',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        [csrfHeader]: this.csrfToken
      },
      body: JSON.stringify(judgment)
    });
    // On the initial save this will provide us with an id for future updates.
    return await res.json();
  }

  /**
   * Get pinned data for the PoolEntryId.
   * @param {Long} poolEntryId
   * @returns
   */
  async getPins(poolEntryId) {
    const url = `${this.contextPath}/data/api/pin/pool_entry/${poolEntryId}`;
    return await this.getJson(url);
  }

  /**
   * Save the pin
   * @param {PinDTO} pin
   * @returns
   */
  async savePin(pin) {
    const { csrfHeader } = this;
    const res = await fetch(`${this.contextPath}/data/api/pin/save_pin`, {
      method: 'post',
      credentials: 'same-origin',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        [csrfHeader]: this.csrfToken
      },
      body: JSON.stringify(pin)
    });
    return await res.json();
  }

  async deletePin(pin) {
    const { csrfHeader } = this;
    const res = await fetch(`${this.contextPath}/data/api/pin/delete_pin`, {
      method: 'post',
      credentials: 'same-origin',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        [csrfHeader]: this.csrfToken
      },
      body: JSON.stringify(pin)
    });
    return res.ok;
  }
}
