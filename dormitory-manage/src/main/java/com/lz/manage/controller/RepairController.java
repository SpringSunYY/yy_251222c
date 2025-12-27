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
import com.lz.manage.model.domain.Repair;
import com.lz.manage.model.vo.repair.RepairVo;
import com.lz.manage.model.dto.repair.RepairQuery;
import com.lz.manage.model.dto.repair.RepairInsert;
import com.lz.manage.model.dto.repair.RepairEdit;
import com.lz.manage.service.IRepairService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 报修记录Controller
 *
 * @author YY
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/manage/repair")
public class RepairController extends BaseController
{
    @Resource
    private IRepairService repairService;

    /**
     * 查询报修记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairQuery repairQuery)
    {
        Repair repair = RepairQuery.queryToObj(repairQuery);
        startPage();
        List<Repair> list = repairService.selectRepairList(repair);
        List<RepairVo> listVo= list.stream().map(RepairVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出报修记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:repair:export')")
    @Log(title = "报修记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepairQuery repairQuery)
    {
        Repair repair = RepairQuery.queryToObj(repairQuery);
        List<Repair> list = repairService.selectRepairList(repair);
        ExcelUtil<Repair> util = new ExcelUtil<Repair>(Repair.class);
        util.exportExcel(response, list, "报修记录数据");
    }

    /**
     * 获取报修记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:repair:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Repair repair = repairService.selectRepairById(id);
        return success(RepairVo.objToVo(repair));
    }

    /**
     * 新增报修记录
     */
    @PreAuthorize("@ss.hasPermi('manage:repair:add')")
    @Log(title = "报修记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairInsert repairInsert)
    {
        Repair repair = RepairInsert.insertToObj(repairInsert);
        return toAjax(repairService.insertRepair(repair));
    }

    /**
     * 修改报修记录
     */
    @PreAuthorize("@ss.hasPermi('manage:repair:edit')")
    @Log(title = "报修记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairEdit repairEdit)
    {
        Repair repair = RepairEdit.editToObj(repairEdit);
        return toAjax(repairService.updateRepair(repair));
    }

    /**
     * 删除报修记录
     */
    @PreAuthorize("@ss.hasPermi('manage:repair:remove')")
    @Log(title = "报修记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(repairService.deleteRepairByIds(ids));
    }

    /**
     * 导入报修记录数据
     */
    @PreAuthorize("@ss.hasPermi('manage:repair:import')")
    @Log(title = "报修记录", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Repair> util = new ExcelUtil<Repair>(Repair.class);
        List<Repair> repairList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = repairService.importRepairData(repairList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载报修记录导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Repair> util = new ExcelUtil<Repair>(Repair.class);
        util.importTemplateExcel(response, "报修记录数据");
    }
}
