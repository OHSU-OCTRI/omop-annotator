import {
  isHex,
  isRgb,
  parseRgb,
  parseHex,
  parseColor,
  isLight,
  isDark
} from '@/utils/color';

describe('color utils', () => {
  describe('isHex', () => {
    it('returns true for valid 6-digit hex colors', () => {
      expect(isHex('#4f7a28')).toBeTrue();
    });
    it('allows uppercase values', () => {
      expect(isHex('#EEFFEE')).toBeTrue();
    });
    it('supports 3 digit hex codes', () => {
      expect(isHex('#ABC')).toBeTrue();
    });
    it('rejects invalid hex letters', () => {
      expect(isHex('#ABZ')).toBeFalse();
    });
    it('returns false for rgb values', () => {
      expect(isHex('rgb(255,165,0')).toBeFalse();
    });
  });

  describe('isRgb', () => {
    it('returns true for valid rgb values', () => {
      expect(isRgb('rgb(0,0,0)')).toBeTrue();
      expect(isRgb('rgb(255,255,255)')).toBeTrue();
    });
    it('allows spaces', () => {
      expect(isRgb('rgb(0, 0, 0)')).toBeTrue();
    });
    it('checks the number of digits for each value', () => {
      expect(isRgb('rgb(1234,0,0)')).toBeFalse();
      expect(isRgb('rgb(0,1234,0)')).toBeFalse();
      expect(isRgb('rgb(0,0,1234)')).toBeFalse();
    });
    it('returns false for hex values', () => {
      expect(isRgb('#ABC')).toBeFalse();
    });
  });

  describe('parseRgb', () => {
    it('parses an rgb string into its component parts', () => {
      expect(parseRgb('rgb(0,100,200)')).toEqual({ r: 0, g: 100, b: 200 });
    });
  });

  describe('parseHex', () => {
    it('parses a hex string into its component parts', () => {
      expect(parseHex('#FFFFFF')).toEqual({ r: 255, g: 255, b: 255 });
      expect(parseHex('#000000')).toEqual({ r: 0, g: 0, b: 0 });
    });
    it('allows 3 digit hex codes', () => {
      expect(parseHex('#FFF')).toEqual({ r: 255, g: 255, b: 255 });
      expect(parseHex('#000')).toEqual({ r: 0, g: 0, b: 0 });
    });
  });

  describe('parseColor', () => {
    it('parses rgb values', () => {
      expect(parseColor('rgb(0,100,200)')).toEqual({ r: 0, g: 100, b: 200 });
    });
    it('parses hex values', () => {
      expect(parseColor('#FFFFFF')).toEqual({ r: 255, g: 255, b: 255 });
    });
  });

  describe('isLight', () => {
    it('returns true for light hex colors (white)', () => {
      expect(isLight('#FFFFFF')).toBeTrue();
    });
    it('returns true for light rgb values (white)', () => {
      expect(isLight('rgb(255,255,255)')).toBeTrue();
    });
    it('returns false for dark hex colors (black)', () => {
      expect(isLight('#000')).toBeFalse();
    });
    it('returns false for dark rgb values (black)', () => {
      expect(isLight('rgb(0,0,0)')).toBeFalse();
    });
  });

  describe('isDark', () => {
    it('returns false for light hex colors (white)', () => {
      expect(isDark('#FFFFFF')).toBeFalse();
    });
    it('returns false for light rgb values (white)', () => {
      expect(isDark('rgb(255,255,255)')).toBeFalse();
    });
    it('returns true for dark hex colors (black)', () => {
      expect(isDark('#000')).toBeTrue();
    });
    it('returns true for dark rgb values (black)', () => {
      expect(isDark('rgb(0,0,0)')).toBeTrue();
    });
  });
});
