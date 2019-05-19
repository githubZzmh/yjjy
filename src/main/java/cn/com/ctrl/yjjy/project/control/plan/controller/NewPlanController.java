package cn.com.ctrl.yjjy.project.control.plan.controller;
import java.io.*;
import java.util.List;
import java.util.UUID;
import cn.com.ctrl.yjjy.project.control.plan.domain.PlanListView;
import cn.com.ctrl.yjjy.project.system.role.service.IRoleService;
import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.com.ctrl.yjjy.project.control.plan.domain.PlanRole;
import cn.com.ctrl.yjjy.project.control.plan.service.IPlanRoleService;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import org.springframework.stereotype.Controller;

/**
 * 角色预案 信息操作处理
 *
 * @author yjjy
 * @date 2018-12-25
 */
@Data
@Log4j2
@Controller
@RequestMapping("/control/plan")
public class NewPlanController extends BaseController {
    private String prefix = "control/plan" ;
    @Autowired
    private IPlanRoleService planRoleService;
    @Autowired
    private IPlanService planService;
    @Autowired
    private IRoleService roleService;
    @RequiresPermissions("control:plan:view")
    @GetMapping()
    public String planRole() {
        return prefix + "/plan" ;
    }

    /**
     * 查询角色预案列表
     */
    @RequiresPermissions("control:plan:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Plan plan) {
        startPage();
        List<Plan> planList = planService.selectPlanList(plan);
        return getDataTable(planList);
    }

    /**
     * 新增预案
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Plan plan = new Plan();
        mmap.put("plan", plan);
        return prefix + "/add" ;
    }

    /**
     * 新增保存预案
     */
    @RequiresPermissions("control:plan:add")
    @Log(title = "角色预案" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Plan plan) throws IOException {
        return toAjax(planService.insertPlan(plan));
    }
    /**
     * 修改预案
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Plan plan =planService.selectPlanById(id);
        mmap.put("plan" , plan);
        return prefix + "/edit" ;
    }
    /**
     * 修改保存预案
     */
    @RequiresPermissions("control:plan:edit")
    @Log(title = "预案" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Plan plan) {
        return toAjax(planService.updatePlan(plan));
    }
    /**
     * 删除预案
     */
    @RequiresPermissions("control:plan:remove")
    @Log(title = "预案" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(planService.deletePlanByIds(ids));
    }
}