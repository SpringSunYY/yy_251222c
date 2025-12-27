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
import com.lz.manage.model.domain.DormitoryApply;
import com.lz.manage.model.vo.dormitoryApply.DormitoryApplyVo;
import com.lz.manage.model.dto.dormitoryApply.DormitoryApplyQuery;
import com.lz.manage.model.dto.dormitoryApply.DormitoryApplyInsert;
import com.lz.manage.model.dto.dormitoryApply.DormitoryApplyEdit;
import com.lz.manage.service.IDormitoryApplyService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 宿舍申请Controller
 *
 * @author YY
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/manage/dormitoryApply")
public class DormitoryApplyController extends BaseController
{
    @Resource
    private IDormitoryApplyService dormitoryApplyService;

    /**
     * 查询宿舍申请列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryApply:list')")
    @GetMapping("/list")
    public TableDataInfo list(DormitoryApplyQuery dormitoryApplyQuery)
    {
        DormitoryApply dormitoryApply = DormitoryApplyQuery.queryToObj(dormitoryApplyQuery);
        startPage();
        List<DormitoryApply> list = dormitoryApplyService.selectDormitoryApplyList(dormitoryApply);
        List<DormitoryApplyVo> listVo= list.stream().map(DormitoryApplyVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出宿舍申请列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryApply:export')")
    @Log(title = "宿舍申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DormitoryApplyQuery dormitoryApplyQuery)
    {
        DormitoryApply dormitoryApply = DormitoryApplyQuery.queryToObj(dormitoryApplyQuery);
        List<DormitoryApply> list = dormitoryApplyService.selectDormitoryApplyList(dormitoryApply);
        ExcelUtil<DormitoryApply> util = new ExcelUtil<DormitoryApply>(DormitoryApply.class);
        util.exportExcel(response, list, "宿舍申请数据");
    }

    /**
     * 获取宿舍申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryApply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        DormitoryApply dormitoryApply = dormitoryApplyService.selectDormitoryApplyById(id);
        return success(DormitoryApplyVo.objToVo(dormitoryApply));
    }

    /**
     * 新增宿舍申请
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryApply:add')")
    @Log(title = "宿舍申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DormitoryApplyInsert dormitoryApplyInsert)
    {
        DormitoryApply dormitoryApply = DormitoryApplyInsert.insertToObj(dormitoryApplyInsert);
        return toAjax(dormitoryApplyService.insertDormitoryApply(dormitoryApply));
    }

    /**
     * 修改宿舍申请
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryApply:edit')")
    @Log(title = "宿舍申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DormitoryApplyEdit dormitoryApplyEdit)
    {
        DormitoryApply dormitoryApply = DormitoryApplyEdit.editToObj(dormitoryApplyEdit);
        return toAjax(dormitoryApplyService.updateDormitoryApply(dormitoryApply));
    }

    /**
     * 删除宿舍申请
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryApply:remove')")
    @Log(title = "宿舍申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dormitoryApplyService.deleteDormitoryApplyByIds(ids));
    }

    /**
     * 导入宿舍申请数据
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryApply:import')")
    @Log(title = "宿舍申请", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<DormitoryApply> util = new ExcelUtil<DormitoryApply>(DormitoryApply.class);
        List<DormitoryApply> dormitoryApplyList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = dormitoryApplyService.importDormitoryApplyData(dormitoryApplyList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载宿舍申请导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<DormitoryApply> util = new ExcelUtil<DormitoryApply>(DormitoryApply.class);
        util.importTemplateExcel(response, "宿舍申请数据");
    }
}
