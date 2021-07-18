import CryptoJS from 'crypto-js'
import CryptoU8array from '@/util/crypto-js-enc-u8array'

// ajax获取图片为arraybuffer格式
export const urlToBuffer = (url, key, iv) => {
  return new Promise(function (resolve, reject) {
    const xhr = new XMLHttpRequest()
    xhr.open('GET', url, true)
    xhr.responseType = 'arraybuffer'
    xhr.onload = function () {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          const url = process(xhr.response, key, iv)
          resolve(url)
        } else {
          retry(url, key, iv)
        }
      }
    }
    xhr.send()
  })
}

// 字符串转字节数组的方法
export const stringToBytes = (str) => {
  let ch = []
  let st = []
  let re = []
  for (let i = 0; i < str.length; i++) {
    ch = str.charCodeAt(i) // get char
    st = [] // set up "stack"
    do {
      st.push(ch & 0xFF) // push byte to stack
      ch = ch >> 8 // shift value down by 1 byte
    }
    while (ch)
    // add stack contents to result
    // done because chars have "wrong" endianness
    re = re.concat(st.reverse())
  }
  // return an array of bytes
  return re
}

// 将上面下载的图片转为base64编码，并处理key和iv的格式
// 解密方式和后端商定为为CBC模式
export const process = (buffer, key, iv) => {
  // 密钥转字节数组(16位)
  const keyBy = stringToBytes(key)
  const ivBy = stringToBytes(iv)

  // 字节数组转Uint8Array
  const keyBv = new Uint8Array(keyBy)
  const ivBv = new Uint8Array(ivBy)

  // Uint8Array转WordArray
  const keyWA = CryptoU8array.u8array.parse(keyBv)
  const ivWA = CryptoU8array.u8array.parse(ivBv)
  const view = new Uint8Array(buffer)

  // 将Uint8Array 转成 WordArray
  const contentWA = CryptoU8array.u8array.parse(view)
  // base64字符串
  const dcBase64String = contentWA.toString(CryptoJS.enc.Base64)

  // 解密
  const decryptedData = CryptoJS.AES.decrypt(dcBase64String, keyWA, {
    iv: ivWA,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  })

  // 把解密后的对象再转为base64编码,这步是关键,跟解密文字不同
  const d64 = decryptedData.toString(CryptoJS.enc.Base64)
  const url = 'data:image/png;base64,' + d64
  return url
}

// 如果获取图片失败,尝试再获取一次 ，图片较少且尺寸较小的话不需要重新获取
export const retry = (url, key, iv) => {
  const xhr = new XMLHttpRequest()
  xhr.open('GET', url, true)
  xhr.responseType = 'arraybuffer'
  xhr.onload = function () {
    if (xhr.status === 200) {
      process(xhr.response, key, iv)
    }
  }
  xhr.send()
}
