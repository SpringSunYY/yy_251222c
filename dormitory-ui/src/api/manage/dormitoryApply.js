import request from '@/utils/request'

// 查询宿舍申请列表
export function listDormitoryApply(query) {
  return request({
    url: '/manage/dormitoryApply/list',
    method: 'get',
    params: query
  })
}

// 查询宿舍申请详细
export function getDormitoryApply(id) {
  return request({
    url: '/manage/dormitoryApply/' + id,
    method: 'get'
  })
}

// 新增宿舍申请
export function addDormitoryApply(data) {
  return request({
    url: '/manage/dormitoryApply',
    method: 'post',
    data: data
  })
}

// 修改宿舍申请
export function updateDormitoryApply(data) {
  return request({
    url: '/manage/dormitoryApply',
    method: 'put',
    data: data
  })
}

// 删除宿舍申请
export function delDormitoryApply(id) {
  return request({
    url: '/manage/dormitoryApply/' + id,
    method: 'delete'
  })
}

// 导入宿舍申请
export function importDormitoryApply(data) {
  return request({
    url: '/manage/dormitoryApply/importData',
    method: 'post',
    data: data
  })
}

// 下载宿舍申请导入模板
export function importTemplateDormitoryApply() {
  return request({
    url: '/manage/dormitoryApply/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
