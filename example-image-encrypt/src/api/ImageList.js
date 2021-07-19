import request from '@/util/request'

// 图片列表
export function getPageImageList () {
  return request({
    url: '/image/imageList',
    method: 'get'
    // params: parms
  })
}
