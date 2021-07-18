import request from '@/util/request'

// 图片列表
export function getPageImageList (parms) {
  return request({
    url: '/image/pageImageList',
    method: 'get',
    params: parms
  })
}
