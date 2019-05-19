package cn.com.ctrl.yjjy.project.rescue.region.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.project.rescue.region.domain.Region;
import cn.com.ctrl.yjjy.project.rescue.region.service.IRegionService;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;

/**
 * 区域 信息操作处理
 *
 * @author yjjy
 * @date 2019-03-14
 */
@Data
@Log4j2
@Controller
@RequestMapping("/rescue/region")
public class RegionController extends BaseController {
    private String prefix = "rescue/region" ;

    @Autowired
    private IRegionService regionService;

    @RequiresPermissions("rescue:region:view")
    @GetMapping()
    public String region() {
        return prefix + "/region" ;
    }

    /**
     * 查询区域列表
     */
    @RequiresPermissions("rescue:region:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Region region) {
        startPage();
        List<Region> list = regionService.selectRegionList(region);
        return getDataTable(list);
    }


    /**
     * 导出区域列表
     */
    @RequiresPermissions("rescue:region:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Region region) {
        List<Region> list = regionService.selectRegionList(region);
        ExcelUtil<Region> util = new ExcelUtil<Region>(Region. class);
        return util.exportExcel(list, "region");
    }

    /**
     * 新增区域
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存区域
     */
    @RequiresPermissions("rescue:region:add")
    @Log(title = "区域" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Region region) {
        return toAjax(regionService.insertRegion(region));
    }

    /**
     * 修改区域
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Region region =regionService.selectRegionById(id);
        mmap.put("region" , region);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存区域
     */
    @RequiresPermissions("rescue:region:edit")
    @Log(title = "区域" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Region region) {
        return toAjax(regionService.updateRegion(region));
    }

    /**
     * 删除区域
     */
    @RequiresPermissions("rescue:region:remove")
    @Log(title = "区域" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(regionService.deleteRegionByIds(ids));
    }

}
