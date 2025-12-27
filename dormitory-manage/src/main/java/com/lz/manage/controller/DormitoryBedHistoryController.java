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
import com.lz.manage.model.domain.DormitoryBedHistory;
import com.lz.manage.model.vo.dormitoryBedHistory.DormitoryBedHistoryVo;
import com.lz.manage.model.dto.dormitoryBedHistory.DormitoryBedHistoryQuery;
import com.lz.manage.model.dto.dormitoryBedHistory.DormitoryBedHistoryInsert;
import com.lz.manage.model.dto.dormitoryBedHistory.DormitoryBedHistoryEdit;
import com.lz.manage.service.IDormitoryBedHistoryService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 床位历史Controller
 *
 * @author YY
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/manage/dormitoryBedHistory")
public class DormitoryBedHistoryController extends BaseController
{
    @Resource
    private IDormitoryBedHistoryService dormitoryBedHistoryService;

    /**
     * 查询床位历史列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBedHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(DormitoryBedHistoryQuery dormitoryBedHistoryQuery)
    {
        DormitoryBedHistory dormitoryBedHistory = DormitoryBedHistoryQuery.queryToObj(dormitoryBedHistoryQuery);
        startPage();
        List<DormitoryBedHistory> list = dormitoryBedHistoryService.selectDormitoryBedHistoryList(dormitoryBedHistory);
        List<DormitoryBedHistoryVo> listVo= list.stream().map(DormitoryBedHistoryVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出床位历史列表
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBedHistory:export')")
    @Log(title = "床位历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DormitoryBedHistoryQuery dormitoryBedHistoryQuery)
    {
        DormitoryBedHistory dormitoryBedHistory = DormitoryBedHistoryQuery.queryToObj(dormitoryBedHistoryQuery);
        List<DormitoryBedHistory> list = dormitoryBedHistoryService.selectDormitoryBedHistoryList(dormitoryBedHistory);
        ExcelUtil<DormitoryBedHistory> util = new ExcelUtil<DormitoryBedHistory>(DormitoryBedHistory.class);
        util.exportExcel(response, list, "床位历史数据");
    }

    /**
     * 获取床位历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBedHistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        DormitoryBedHistory dormitoryBedHistory = dormitoryBedHistoryService.selectDormitoryBedHistoryById(id);
        return success(DormitoryBedHistoryVo.objToVo(dormitoryBedHistory));
    }

    /**
     * 新增床位历史
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBedHistory:add')")
    @Log(title = "床位历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DormitoryBedHistoryInsert dormitoryBedHistoryInsert)
    {
        DormitoryBedHistory dormitoryBedHistory = DormitoryBedHistoryInsert.insertToObj(dormitoryBedHistoryInsert);
        return toAjax(dormitoryBedHistoryService.insertDormitoryBedHistory(dormitoryBedHistory));
    }

    /**
     * 修改床位历史
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBedHistory:edit')")
    @Log(title = "床位历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DormitoryBedHistoryEdit dormitoryBedHistoryEdit)
    {
        DormitoryBedHistory dormitoryBedHistory = DormitoryBedHistoryEdit.editToObj(dormitoryBedHistoryEdit);
        return toAjax(dormitoryBedHistoryService.updateDormitoryBedHistory(dormitoryBedHistory));
    }

    /**
     * 删除床位历史
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBedHistory:remove')")
    @Log(title = "床位历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dormitoryBedHistoryService.deleteDormitoryBedHistoryByIds(ids));
    }

    /**
     * 导入床位历史数据
     */
    @PreAuthorize("@ss.hasPermi('manage:dormitoryBedHistory:import')")
    @Log(title = "床位历史", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<DormitoryBedHistory> util = new ExcelUtil<DormitoryBedHistory>(DormitoryBedHistory.class);
        List<DormitoryBedHistory> dormitoryBedHistoryList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = dormitoryBedHistoryService.importDormitoryBedHistoryData(dormitoryBedHistoryList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载床位历史导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<DormitoryBedHistory> util = new ExcelUtil<DormitoryBedHistory>(DormitoryBedHistory.class);
        util.importTemplateExcel(response, "床位历史数据");
    }
}
