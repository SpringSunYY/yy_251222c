import request from '@/utils/request'

// 查询床位历史列表
export function listDormitoryBedHistory(query) {
  return request({
    url: '/manage/dormitoryBedHistory/list',
    method: 'get',
    params: query
  })
}

// 查询床位历史详细
export function getDormitoryBedHistory(id) {
  return request({
    url: '/manage/dormitoryBedHistory/' + id,
    method: 'get'
  })
}

// 新增床位历史
export function addDormitoryBedHistory(data) {
  return request({
    url: '/manage/dormitoryBedHistory',
    method: 'post',
    data: data
  })
}

// 修改床位历史
export function updateDormitoryBedHistory(data) {
  return request({
    url: '/manage/dormitoryBedHistory',
    method: 'put',
    data: data
  })
}

// 删除床位历史
export function delDormitoryBedHistory(id) {
  return request({
    url: '/manage/dormitoryBedHistory/' + id,
    method: 'delete'
  })
}

// 导入床位历史
export function importDormitoryBedHistory(data) {
  return request({
    url: '/manage/dormitoryBedHistory/importData',
    method: 'post',
    data: data
  })
}

// 下载床位历史导入模板
export function importTemplateDormitoryBedHistory() {
  return request({
    url: '/manage/dormitoryBedHistory/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
