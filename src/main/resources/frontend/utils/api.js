const defaultFetchOptions = Object.freeze({ credentials: 'same-origin' });

/**
 * A simple wrapper for accessing API endpoints.
 */
export default class Api {
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
