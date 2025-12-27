import request from '@/utils/request'

// 查询维修工单列表
export function listRepairWorkOrder(query) {
  return request({
    url: '/manage/repairWorkOrder/list',
    method: 'get',
    params: query
  })
}

// 查询维修工单详细
export function getRepairWorkOrder(id) {
  return request({
    url: '/manage/repairWorkOrder/' + id,
    method: 'get'
  })
}

// 新增维修工单
export function addRepairWorkOrder(data) {
  return request({
    url: '/manage/repairWorkOrder',
    method: 'post',
    data: data
  })
}

// 修改维修工单
export function updateRepairWorkOrder(data) {
  return request({
    url: '/manage/repairWorkOrder',
    method: 'put',
    data: data
  })
}

// 删除维修工单
export function delRepairWorkOrder(id) {
  return request({
    url: '/manage/repairWorkOrder/' + id,
    method: 'delete'
  })
}

// 导入维修工单
export function importRepairWorkOrder(data) {
  return request({
    url: '/manage/repairWorkOrder/importData',
    method: 'post',
    data: data
  })
}

// 下载维修工单导入模板
export function importTemplateRepairWorkOrder() {
  return request({
    url: '/manage/repairWorkOrder/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
