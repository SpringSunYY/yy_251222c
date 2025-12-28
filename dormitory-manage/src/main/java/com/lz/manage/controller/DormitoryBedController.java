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
import com.lz.manage.model.domain.DormitoryBed;
import com.lz.manage.model.vo.dormitoryBed.DormitoryBedVo;
import com.lz.manage.model.dto.dormitoryBed.DormitoryBedQuery;
import com.lz.manage.model.dto.dormitoryBed.DormitoryBedInsert;
import com.lz.manage.model.dto.dormitoryBed.DormitoryBedEdit;
import com.lz.manage.service.IDormitoryBedService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 宿舍床位Controller
 *
 * @author YY
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/manage/dormitoryBed")
public class DormitoryBedController extends BaseController
{
    @Resource
    private IDormitoryBedService dormitoryBedService;

    /**
     * 查询宿舍床位列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:dormitoryBed:list,manage:dormitoryBed:query')")
    @GetMapping("/list")
    public TableDataInfo list(DormitoryBedQuery dormitoryBedQuery)
    {
        DormitoryBed dormitoryBed = DormitoryBedQuery.queryToObj(dormitoryBedQuery);
        startPage();
        List<DormitoryBed> list = dormitoryBedService.selectDormitoryBedList(dormitoryBed);
        List<DormitoryBedVo> listVo= list.stream().map(DormitoryBedVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出宿舍床位列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBed:export')")
    @Log(title = "宿舍床位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DormitoryBedQuery dormitoryBedQuery)
    {
        DormitoryBed dormitoryBed = DormitoryBedQuery.queryToObj(dormitoryBedQuery);
        List<DormitoryBed> list = dormitoryBedService.selectDormitoryBedList(dormitoryBed);
        ExcelUtil<DormitoryBed> util = new ExcelUtil<DormitoryBed>(DormitoryBed.class);
        util.exportExcel(response, list, "宿舍床位数据");
    }

    /**
     * 获取宿舍床位详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBed:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        DormitoryBed dormitoryBed = dormitoryBedService.selectDormitoryBedById(id);
        return success(DormitoryBedVo.objToVo(dormitoryBed));
    }

    /**
     * 新增宿舍床位
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBed:add')")
    @Log(title = "宿舍床位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DormitoryBedInsert dormitoryBedInsert)
    {
        DormitoryBed dormitoryBed = DormitoryBedInsert.insertToObj(dormitoryBedInsert);
        return toAjax(dormitoryBedService.insertDormitoryBed(dormitoryBed));
    }

    /**
     * 修改宿舍床位
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBed:edit')")
    @Log(title = "宿舍床位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DormitoryBedEdit dormitoryBedEdit)
    {
        DormitoryBed dormitoryBed = DormitoryBedEdit.editToObj(dormitoryBedEdit);
        return toAjax(dormitoryBedService.updateDormitoryBed(dormitoryBed));
    }

    /**
     * 分配床位
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBed:edit')")
    @Log(title = "宿舍床位", businessType = BusinessType.UPDATE)
    @PutMapping("/allot")
    public AjaxResult allot(@RequestBody DormitoryBedEdit dormitoryBedEdit){
        DormitoryBed dormitoryBed = DormitoryBedEdit.editToObj(dormitoryBedEdit);
        return toAjax(dormitoryBedService.allotDormitoryBed(dormitoryBed));
    }

    /**
     * 删除宿舍床位
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBed:remove')")
    @Log(title = "宿舍床位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dormitoryBedService.deleteDormitoryBedByIds(ids));
    }

    /**
     * 导入宿舍床位数据
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBed:import')")
    @Log(title = "宿舍床位", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<DormitoryBed> util = new ExcelUtil<DormitoryBed>(DormitoryBed.class);
        List<DormitoryBed> dormitoryBedList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = dormitoryBedService.importDormitoryBedData(dormitoryBedList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载宿舍床位导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<DormitoryBed> util = new ExcelUtil<DormitoryBed>(DormitoryBed.class);
        util.importTemplateExcel(response, "宿舍床位数据");
    }
}
