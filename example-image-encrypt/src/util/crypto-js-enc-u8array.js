import CryptoJS from 'crypto-js'

CryptoJS.enc.u8array = {
  /**
   * Converts a word array to a Uint8Array.
   *
   * @param {WordArray} wordArray The word array.
   *
   * @return {Uint8Array} The Uint8Array.
   *
   * @static
   *
   * @example
   *
   * let u8arr = CryptoJS.enc.u8array.stringify(wordArray);
   */
  stringify: function (wordArray) {
    // Shortcuts
    const words = wordArray.words
    const sigBytes = wordArray.sigBytes
    // Convert
    const u8 = new Uint8Array(sigBytes)
    for (let i = 0; i < sigBytes; i++) {
      const byte = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff
      u8[i] = byte
    }
    return u8
  },
  /**
   * Converts a Uint8Array to a word array.
   *
   * @param {string} u8Str The Uint8Array.
   *
   * @return {WordArray} The word array.
   *
   * @static
   *
   * @example
   *
   * let wordArray = CryptoJS.enc.u8array.parse(u8arr);
   */
  parse: function (u8arr) {
    // Shortcut
    const len = u8arr.length
    // Convert
    const words = []
    for (let i = 0; i < len; i++) {
      words[i >>> 2] |= (u8arr[i] & 0xff) << (24 - (i % 4) * 8)
    }
    return CryptoJS.lib.WordArray.create(words, len)
  }
}
export default {
  u8array: CryptoJS.enc.u8array
}
