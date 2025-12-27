import request from '@/utils/request'

// 查询报修记录列表
export function listRepair(query) {
  return request({
    url: '/manage/repair/list',
    method: 'get',
    params: query
  })
}

// 查询报修记录详细
export function getRepair(id) {
  return request({
    url: '/manage/repair/' + id,
    method: 'get'
  })
}

// 新增报修记录
export function addRepair(data) {
  return request({
    url: '/manage/repair',
    method: 'post',
    data: data
  })
}

// 修改报修记录
export function updateRepair(data) {
  return request({
    url: '/manage/repair',
    method: 'put',
    data: data
  })
}

// 删除报修记录
export function delRepair(id) {
  return request({
    url: '/manage/repair/' + id,
    method: 'delete'
  })
}

// 导入报修记录
export function importRepair(data) {
  return request({
    url: '/manage/repair/importData',
    method: 'post',
    data: data
  })
}

// 下载报修记录导入模板
export function importTemplateRepair() {
  return request({
    url: '/manage/repair/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
