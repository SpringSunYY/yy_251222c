import request from '@/utils/request'

// 查询宿舍床位列表
export function listDormitoryBed(query) {
  return request({
    url: '/manage/dormitoryBed/list',
    method: 'get',
    params: query
  })
}

// 查询宿舍床位详细
export function getDormitoryBed(id) {
  return request({
    url: '/manage/dormitoryBed/' + id,
    method: 'get'
  })
}

// 新增宿舍床位
export function addDormitoryBed(data) {
  return request({
    url: '/manage/dormitoryBed',
    method: 'post',
    data: data
  })
}

// 修改宿舍床位
export function updateDormitoryBed(data) {
  return request({
    url: '/manage/dormitoryBed',
    method: 'put',
    data: data
  })
}

//分配宿舍床位
export function allotDormitoryBed(data) {
  return request({
    url: '/manage/dormitoryBed/allot',
    method: 'put',
    data: data
  })
}

// 删除宿舍床位
export function delDormitoryBed(id) {
  return request({
    url: '/manage/dormitoryBed/' + id,
    method: 'delete'
  })
}

// 导入宿舍床位
export function importDormitoryBed(data) {
  return request({
    url: '/manage/dormitoryBed/importData',
    method: 'post',
    data: data
  })
}

// 下载宿舍床位导入模板
export function importTemplateDormitoryBed() {
  return request({
    url: '/manage/dormitoryBed/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
