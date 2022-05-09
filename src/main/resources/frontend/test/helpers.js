const defaultOptions = Object.freeze({ status: 200, statusText: 'OK' });

/**
 * Creates a fetch response that resolves with the specified JSON data.
 *
 * @param {any} jsonData - data to include in the response. Will be stringified if needed.
 * @param {Object} options - fetch response options (status, statusText, headers). Defaults
 *  to `{ status: 200, statusText: 'OK' }`, mimicking a successful GET request.
 * @returns {Response}
 */
export function mockFetchResponse(jsonData, options = defaultOptions) {
  const responseData = typeof jsonData === 'string' ? jsonData : JSON.stringify(jsonData);
  return new Response(responseData, options);
}

/**
 * Sometimes the built-in isVisible function for Vue tests does not work
 * @param {*} wrapper
 */
export function isVisible(wrapper) {
  const styleAttr = wrapper.attributes('style');
  const classAttr = wrapper.attributes('class');
  if (styleAttr !== undefined && styleAttr.includes('display: none;')) {
    return false;
  } else if (classAttr !== undefined && classAttr.includes('d-none')) {
    return false;
  }

  return true;
}
