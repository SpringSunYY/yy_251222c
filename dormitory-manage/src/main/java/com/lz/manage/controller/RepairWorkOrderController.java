package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.RepairWorkOrder;
import com.lz.manage.model.vo.repairWorkOrder.RepairWorkOrderVo;
import com.lz.manage.model.dto.repairWorkOrder.RepairWorkOrderQuery;
import com.lz.manage.model.dto.repairWorkOrder.RepairWorkOrderInsert;
import com.lz.manage.model.dto.repairWorkOrder.RepairWorkOrderEdit;
import com.lz.manage.service.IRepairWorkOrderService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 维修工单Controller
 *
 * @author YY
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/manage/repairWorkOrder")
public class RepairWorkOrderController extends BaseController
{
    @Resource
    private IRepairWorkOrderService repairWorkOrderService;

    /**
     * 查询维修工单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:repairWorkOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairWorkOrderQuery repairWorkOrderQuery)
    {
        RepairWorkOrder repairWorkOrder = RepairWorkOrderQuery.queryToObj(repairWorkOrderQuery);
        startPage();
        List<RepairWorkOrder> list = repairWorkOrderService.selectRepairWorkOrderList(repairWorkOrder);
        List<RepairWorkOrderVo> listVo= list.stream().map(RepairWorkOrderVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出维修工单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:repairWorkOrder:export')")
    @Log(title = "维修工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepairWorkOrderQuery repairWorkOrderQuery)
    {
        RepairWorkOrder repairWorkOrder = RepairWorkOrderQuery.queryToObj(repairWorkOrderQuery);
        List<RepairWorkOrder> list = repairWorkOrderService.selectRepairWorkOrderList(repairWorkOrder);
        ExcelUtil<RepairWorkOrder> util = new ExcelUtil<RepairWorkOrder>(RepairWorkOrder.class);
        util.exportExcel(response, list, "维修工单数据");
    }

    /**
     * 获取维修工单详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:repairWorkOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        RepairWorkOrder repairWorkOrder = repairWorkOrderService.selectRepairWorkOrderById(id);
        return success(RepairWorkOrderVo.objToVo(repairWorkOrder));
    }

    /**
     * 新增维修工单
     */
    @PreAuthorize("@ss.hasPermi('manage:repairWorkOrder:add')")
    @Log(title = "维修工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairWorkOrderInsert repairWorkOrderInsert)
    {
        RepairWorkOrder repairWorkOrder = RepairWorkOrderInsert.insertToObj(repairWorkOrderInsert);
        return toAjax(repairWorkOrderService.insertRepairWorkOrder(repairWorkOrder));
    }

    /**
     * 修改维修工单
     */
    @PreAuthorize("@ss.hasPermi('manage:repairWorkOrder:edit')")
    @Log(title = "维修工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairWorkOrderEdit repairWorkOrderEdit)
    {
        RepairWorkOrder repairWorkOrder = RepairWorkOrderEdit.editToObj(repairWorkOrderEdit);
        return toAjax(repairWorkOrderService.updateRepairWorkOrder(repairWorkOrder));
    }

    /**
     * 删除维修工单
     */
    @PreAuthorize("@ss.hasPermi('manage:repairWorkOrder:remove')")
    @Log(title = "维修工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(repairWorkOrderService.deleteRepairWorkOrderByIds(ids));
    }

    /**
     * 导入维修工单数据
     */
    @PreAuthorize("@ss.hasPermi('manage:repairWorkOrder:import')")
    @Log(title = "维修工单", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<RepairWorkOrder> util = new ExcelUtil<RepairWorkOrder>(RepairWorkOrder.class);
        List<RepairWorkOrder> repairWorkOrderList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = repairWorkOrderService.importRepairWorkOrderData(repairWorkOrderList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载维修工单导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<RepairWorkOrder> util = new ExcelUtil<RepairWorkOrder>(RepairWorkOrder.class);
        util.importTemplateExcel(response, "维修工单数据");
    }
}
