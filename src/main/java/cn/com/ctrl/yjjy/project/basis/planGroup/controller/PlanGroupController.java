package cn.com.ctrl.yjjy.basis.basis.planGroup.controller;

import java.util.List;

import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
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
import cn.com.ctrl.yjjy.project.basis.planGroup.domain.PlanGroup;
import cn.com.ctrl.yjjy.project.basis.planGroup.service.IPlanGroupService;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;

/**
 * 预案分组 信息操作处理
 *
 * @author yjjy
 * @date 2019-04-16
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/planGroup")
public class PlanGroupController extends BaseController {
    private String prefix = "basis/planGroup" ;

    @Autowired
    private IPlanGroupService planGroupService;
    @Autowired
    private IPlanService planService;

    @RequiresPermissions("basis:group:view")
    @GetMapping("/list/{catId}")
    public String planGroup(@PathVariable("catId") String catId, ModelMap mmap) {
//        Plan plan = planService.selectPlanById(catId);
        mmap.put("planId",catId);
        return prefix + "/planGroup" ;
    }

    /**
     * 查询预案分组列表
     */
    @RequiresPermissions("basis:group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PlanGroup planGroup) {
        startPage();
        List<PlanGroup> list = planGroupService.selectPlanGroupList(planGroup);
        return getDataTable(list);
    }


    /**
     * 导出预案分组列表
     */
    @RequiresPermissions("basis:group:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PlanGroup planGroup) {
        List<PlanGroup> list = planGroupService.selectPlanGroupList(planGroup);
        ExcelUtil<PlanGroup> util = new ExcelUtil<PlanGroup>(PlanGroup. class);
        return util.exportExcel(list, "planGroup");
    }

    /**
     * 新增预案分组
     */
    @GetMapping("/add/{planId}")
    public String add(@PathVariable("planId") String planId, ModelMap mmap) {
        Plan plan = planService.selectPlanById(planId);
        mmap.put("planId",planId);
        mmap.put("planName",plan.getName());
        return prefix + "/add" ;
    }

    /**
     * 新增保存预案分组
     */
    @RequiresPermissions("basis:group:add")
    @Log(title = "预案分组" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PlanGroup planGroup) {
        return toAjax(planGroupService.insertPlanGroup(planGroup));
    }

    /**
     * 修改预案分组
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        PlanGroup planGroup =planGroupService.selectPlanGroupById(id);
        mmap.put("planGroup" , planGroup);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存预案分组
     */
    @RequiresPermissions("basis:group:edit")
    @Log(title = "预案分组" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PlanGroup planGroup) {
        return toAjax(planGroupService.updatePlanGroup(planGroup));
    }

    /**
     * 删除预案分组
     */
    @RequiresPermissions("basis:group:remove")
    @Log(title = "预案分组" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(planGroupService.deletePlanGroupByIds(ids));
    }

}
