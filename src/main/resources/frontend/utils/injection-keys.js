/**
 * Constants for names of properties that can be injected into components.
 */

/**
 * Key to inject the application context path.
 */
export const contextPath = 'contextPath';

/**
 * Key to inject the CSRF token.
 */
export const csrfToken = 'csrfToken';

/**
 * Key to inject the name of the CSRF token header.
 */
export const csrfHeader = 'csrfHeader';

/**
 * All injectable keys, exported as a single object.
 */
export default Object.freeze({
  contextPath,
  csrfToken,
  csrfHeader
});
