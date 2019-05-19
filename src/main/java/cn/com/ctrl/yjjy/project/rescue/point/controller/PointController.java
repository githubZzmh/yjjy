package cn.com.ctrl.yjjy.project.rescue.point.controller;

import java.util.*;

import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.project.control.plan.service.IPointService;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
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
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;

/**
 * 百度地图点 信息操作处理
 *
 * @author yjjy
 * @date 2019-03-12
 */
@Log4j2
@Controller
@RequestMapping("/rescue/point")
public class PointController extends BaseController {
    private String prefix = "control/point" ;
    @Autowired
    private IPointService pointService;
    @Autowired
    private IShebeiService shebeiService;

    @RequiresPermissions("rescue:point:view")
    @GetMapping()
    public String point() {
        return prefix + "/point" ;
    }

    /**
     * 查询百度地图点列表
     */
    @RequiresPermissions("rescue:point:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Point point) {
        startPage();
        List<Point> list = pointService.selectPointList(point);
        return getDataTable(list);
    }
    /**
     * 导出百度地图点列表
     */
    @RequiresPermissions("control:point:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Point point) {
        List<Point> list = pointService.selectPointList(point);
        ExcelUtil<Point> util = new ExcelUtil<Point>(Point. class);
        return util.exportExcel(list, "point");
    }
    /**
     * 新增百度地图点
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Shebei shebei = new Shebei();
        List<Shebei> shebeiList = shebeiService.selectShebeiList(shebei);
        mmap.put("shebeiList", shebeiList);
        return prefix + "/add" ;
    }
    /**
     * 新增保存百度地图点
     */
    @RequiresPermissions("rescue:point:view")
    @Log(title = "百度地图点" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Point point) {
        return toAjax(pointService.insertPoint(point));
    }
    /**
     * 根据设备id查询设备
     * @param shebeiId
     * @return
     */
    @PostMapping("/selectShebei")
    @ResponseBody
    public Map selectSystem(String shebeiId) {
        Map map = new HashMap();
        Shebei shebei = shebeiService.selectShebeiById(shebeiId);
        map.put("shebei",shebei);
        return map;
    }
    /**
     * 修改百度地图点
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Point point =pointService.selectPointById(id);
        mmap.put("point" , point);
        return prefix + "/edit" ;
    }
    /**
     * 修改保存百度地图点
     */
    @RequiresPermissions("control:point:edit")
    @Log(title = "百度地图点" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Point point) {
        return toAjax(pointService.updatePoint(point));
    }
    /**
     * 删除百度地图点
     */
    @RequiresPermissions("rescue:point:view")
    @Log(title = "百度地图点" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(pointService.deletePointByIds(ids));
    }
}