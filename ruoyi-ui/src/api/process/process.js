// 查询部门下拉树结构
import request from "@/utils/request";
export function croptype() {
  return request({
    url: '/pro/croptype/typeList',
    method: 'get'
  })
}

// 查询加工产品
export function listall(data) {
  return request({
    url: '/pro/process/list',
    method: 'put',
    data: data
  })
}

// 查询还未加工的产品
export function nolist() {
  return request({
    url: '/pro/process/nolist',
    method: 'get',
  })
}

// 新增加工产品
export function saveprocess(data) {
  return request({
    url: '/pro/process/saveprocess',
    method: 'put',
    data: data
  })
}

// 回显
export function updateprocess(id) {
  return request({
    url: '/pro/process/updateprocess?id=' + id,
    method: 'put',
  })
}

// 修改加工状态
export function proupByid(id) {
  return request({
    url: '/pro/process/proupByid?id=' + id,
    method: 'put',
  })
}

// 回显
export function delprocess(id) {
  return request({
    url: '/pro/process/delprocess?id=' + id,
    method: 'put',
  })
}

  // 修改数据
export function updatepro(data) {
    return request({
      url: '/pro/process/updatepro',
      method: 'put',
      data: data
    })
}

// 回显
export function getxy() {
    return request({
      url: '/pro/bt/sumallmes',
      method: 'put',
    })
}
// 饼状
export function getbz() {
    return request({
      url: '/pro/bt/BingBaoBiao',
      method: 'put',
    })
}






