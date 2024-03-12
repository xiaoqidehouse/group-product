import request from "@/utils/request";
//一级下拉框显示
export function invstashlist() {
  return request({
    url: '/inv/inv/invstash',
    method: 'get'
  })
}

// 二级下拉框改变发生
export function twochange(id) {
  return request({
    url: '/inv/inv/changbyid?id=' + id,
    method: 'put',
  })
}

// 逻辑删除(flag)
export function delinv(id) {
  return request({
    url: '/inv/inv/delinvByid?id=' + id,
    method: 'put',
  })
}

// 根据状态查找符合的数据(flag)
export function seleflag(flag) {
  return request({
    url: '/inv/inv/selByFlag?flag=' + flag,
    method: 'put'
  })
}

// 查询所有产品
export function listall(data) {
  return request({
    url: '/inv/inv/listall',
    method: 'put',
    data: data
  })
}

//所有类型
export function allstaustype() {
  return request({
    url: '/inv/inv/staustype',
    method: 'get'
  })
}

// 修改数据
export function updateinvupdate(data) {
  return request({
    url: '/inv/inv/invupdate',
    method: 'put',
    data: data
  })
}
//工作流申请的用户
export function worklist(roleName) {
    return request({
        url: '/act/act/allList?roleName=' + roleName,
        method: 'post',
    })
}
// 登录
export function loginfrom(data) {
  return request({
    url: '/act/act/login',
    method: 'post',
    data: data
  })
}
// 申请
export function saveprocess(data) {
  return request({
    url: '/act/act/savework',
    method: 'put',
    data: data
  })
}

// 查看历史流程
export function queryhis() {
  return request({
    url: '/act/act/findhis?roleName',
    method: 'put'
  })
}

// 导出300万
export function userExcel() {
  return request({
    url: '/inv/inv/userExcel',
    method: 'post',
  })
}
