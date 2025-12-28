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
import com.lz.manage.model.domain.Dormitory;
import com.lz.manage.model.vo.dormitory.DormitoryVo;
import com.lz.manage.model.dto.dormitory.DormitoryQuery;
import com.lz.manage.model.dto.dormitory.DormitoryInsert;
import com.lz.manage.model.dto.dormitory.DormitoryEdit;
import com.lz.manage.service.IDormitoryService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 宿舍Controller
 *
 * @author YY
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/manage/dormitory")
public class DormitoryController extends BaseController
{
    @Resource
    private IDormitoryService dormitoryService;

    /**
     * 查询宿舍列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:dormitory:list,manage:dormitory:query')")
    @GetMapping("/list")
    public TableDataInfo list(DormitoryQuery dormitoryQuery)
    {
        Dormitory dormitory = DormitoryQuery.queryToObj(dormitoryQuery);
        startPage();
        List<Dormitory> list = dormitoryService.selectDormitoryList(dormitory);
        List<DormitoryVo> listVo= list.stream().map(DormitoryVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出宿舍列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitory:export')")
    @Log(title = "宿舍", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DormitoryQuery dormitoryQuery)
    {
        Dormitory dormitory = DormitoryQuery.queryToObj(dormitoryQuery);
        List<Dormitory> list = dormitoryService.selectDormitoryList(dormitory);
        ExcelUtil<Dormitory> util = new ExcelUtil<Dormitory>(Dormitory.class);
        util.exportExcel(response, list, "宿舍数据");
    }

    /**
     * 获取宿舍详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Dormitory dormitory = dormitoryService.selectDormitoryById(id);
        return success(DormitoryVo.objToVo(dormitory));
    }

    /**
     * 新增宿舍
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitory:add')")
    @Log(title = "宿舍", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DormitoryInsert dormitoryInsert)
    {
        Dormitory dormitory = DormitoryInsert.insertToObj(dormitoryInsert);
        return toAjax(dormitoryService.insertDormitory(dormitory));
    }

    /**
     * 修改宿舍
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitory:edit')")
    @Log(title = "宿舍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DormitoryEdit dormitoryEdit)
    {
        Dormitory dormitory = DormitoryEdit.editToObj(dormitoryEdit);
        return toAjax(dormitoryService.updateDormitory(dormitory));
    }

    /**
     * 删除宿舍
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitory:remove')")
    @Log(title = "宿舍", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dormitoryService.deleteDormitoryByIds(ids));
    }

    /**
     * 导入宿舍数据
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitory:import')")
    @Log(title = "宿舍", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Dormitory> util = new ExcelUtil<Dormitory>(Dormitory.class);
        List<Dormitory> dormitoryList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = dormitoryService.importDormitoryData(dormitoryList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载宿舍导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Dormitory> util = new ExcelUtil<Dormitory>(Dormitory.class);
        util.importTemplateExcel(response, "宿舍数据");
    }
}
