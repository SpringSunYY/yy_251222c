import request from '@/utils/request'

// 查询宿舍列表
export function listDormitory(query) {
  return request({
    url: '/manage/dormitory/list',
    method: 'get',
    params: query
  })
}

// 查询宿舍详细
export function getDormitory(id) {
  return request({
    url: '/manage/dormitory/' + id,
    method: 'get'
  })
}

// 新增宿舍
export function addDormitory(data) {
  return request({
    url: '/manage/dormitory',
    method: 'post',
    data: data
  })
}

// 修改宿舍
export function updateDormitory(data) {
  return request({
    url: '/manage/dormitory',
    method: 'put',
    data: data
  })
}

// 删除宿舍
export function delDormitory(id) {
  return request({
    url: '/manage/dormitory/' + id,
    method: 'delete'
  })
}

// 导入宿舍
export function importDormitory(data) {
  return request({
    url: '/manage/dormitory/importData',
    method: 'post',
    data: data
  })
}

// 下载宿舍导入模板
export function importTemplateDormitory() {
  return request({
    url: '/manage/dormitory/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
