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
import com.lz.manage.model.domain.Building;
import com.lz.manage.model.vo.building.BuildingVo;
import com.lz.manage.model.dto.building.BuildingQuery;
import com.lz.manage.model.dto.building.BuildingInsert;
import com.lz.manage.model.dto.building.BuildingEdit;
import com.lz.manage.service.IBuildingService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 楼栋信息Controller
 *
 * @author YY
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/manage/building")
public class BuildingController extends BaseController
{
    @Resource
    private IBuildingService buildingService;

    /**
     * 查询楼栋信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:building:list')")
    @GetMapping("/list")
    public TableDataInfo list(BuildingQuery buildingQuery)
    {
        Building building = BuildingQuery.queryToObj(buildingQuery);
        startPage();
        List<Building> list = buildingService.selectBuildingList(building);
        List<BuildingVo> listVo= list.stream().map(BuildingVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出楼栋信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:building:export')")
    @Log(title = "楼栋信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BuildingQuery buildingQuery)
    {
        Building building = BuildingQuery.queryToObj(buildingQuery);
        List<Building> list = buildingService.selectBuildingList(building);
        ExcelUtil<Building> util = new ExcelUtil<Building>(Building.class);
        util.exportExcel(response, list, "楼栋信息数据");
    }

    /**
     * 获取楼栋信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:building:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Building building = buildingService.selectBuildingById(id);
        return success(BuildingVo.objToVo(building));
    }

    /**
     * 新增楼栋信息
     */
    @PreAuthorize("@ss.hasPermi('manage:building:add')")
    @Log(title = "楼栋信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BuildingInsert buildingInsert)
    {
        Building building = BuildingInsert.insertToObj(buildingInsert);
        return toAjax(buildingService.insertBuilding(building));
    }

    /**
     * 修改楼栋信息
     */
    @PreAuthorize("@ss.hasPermi('manage:building:edit')")
    @Log(title = "楼栋信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BuildingEdit buildingEdit)
    {
        Building building = BuildingEdit.editToObj(buildingEdit);
        return toAjax(buildingService.updateBuilding(building));
    }

    /**
     * 删除楼栋信息
     */
    @PreAuthorize("@ss.hasPermi('manage:building:remove')")
    @Log(title = "楼栋信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(buildingService.deleteBuildingByIds(ids));
    }

    /**
     * 导入楼栋信息数据
     */
    @PreAuthorize("@ss.hasPermi('manage:building:import')")
    @Log(title = "楼栋信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Building> util = new ExcelUtil<Building>(Building.class);
        List<Building> buildingList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = buildingService.importBuildingData(buildingList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载楼栋信息导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Building> util = new ExcelUtil<Building>(Building.class);
        util.importTemplateExcel(response, "楼栋信息数据");
    }
}
