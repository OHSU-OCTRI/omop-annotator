const RGB_REGEX = /^rgb\((\d{1,3}),\s*(\d{1,3}),\s*(\d{1,3})\)$/;
const HEX_REGEX = /^#([a-fA-F0-9]{3}){1,2}$/;

/**
 * Test if the given string is formatted as a hexadecimal color.
 * @param {string} color
 * @returns
 */
export function isHex(color) {
  return HEX_REGEX.test(color);
}

/**
 * Test if the given string is formatted as a rgb color.
 * @param {string} color
 * @returns
 */
export function isRgb(color) {
  return RGB_REGEX.test(color);
}

/**
 * Extract rgb components from a rgb representation of a color
 * @param {string} color
 * @returns {{r: number, g: number, b: number}}
 */
export function parseRgb(color) {
  if (isRgb(color)) {
    color = color.match(RGB_REGEX);
    return { r: parseInt(color[1]), g: parseInt(color[2]), b: parseInt(color[3]) };
  }
  throw new Error('Argument is not formatted as a rgb color.');
}

/**
 * Extract rgb components from a hex string
 * @param {string} hex - hexadecimal color
 * @returns {{r: number, g: number, b: number}}
 */
export function parseHex(hex) {
  if (isHex(hex)) {
    // Adapted from https://stackoverflow.com/questions/12943774/hex-to-rgb-converter
    if (hex.length === 4) {
      hex = '#' + [hex[1], hex[1], hex[2], hex[2], hex[3], hex[3]].join('');
    }
    const color = '0x' + hex.substring(1);
    return { r: (color >> 16) & 255, g: (color >> 8) & 255, b: color & 255 };
  }
  throw new Error('Argument is not formatted as a hexadecimal color.');
}

/**
 * Extract rgb components from a string representation of a color.
 * @param {string} color
 * @returns {{r: number, g: number, b: number}}
 */
export function parseColor(color) {
  if (isHex(color)) {
    return parseHex(color);
  }
  if (isRgb(color)) {
    return parseRgb(color);
  }
  throw new Error(`Unrecognized color format: ${color}`);
}
/**
 * Calculate a perceived brightness value for the given RGB value, based off an
 * equation from http://alienryderflex.com/hsp.html.
 *
 * @param {{r: number, g: number, b: number}} rgb
 * @returns
 */
function perceivedBrightness(rgb) {
  return Math.sqrt(
    0.299 * (rgb.r * rgb.r) + 0.587 * (rgb.g * rgb.g) + 0.114 * (rgb.b * rgb.b)
  );
}

// from https://awik.io/determine-color-bright-dark-using-javascript/
const MAX_DARK_COLOR_BRIGHTNESS = 127.5;

/**
 * Determine if the provided color is perceived as light.
 * @param {string} color - hex or rgb color
 * @returns {boolean}
 */
export function isLight(color) {
  return perceivedBrightness(parseColor(color)) > MAX_DARK_COLOR_BRIGHTNESS;
}

/**
 * Determine if the provided color is perceived as dark.
 * @param {string} color - hex or rgb color
 * @returns {boolean}
 */
export function isDark(color) {
  return !isLight(color);
}
