package cn.com.ctrl.yjjy.project.basis.planPerson.controller;

import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.planGroup.domain.PlanGroup;
import cn.com.ctrl.yjjy.project.basis.planGroup.service.IPlanGroupService;
import cn.com.ctrl.yjjy.project.basis.planPerson.domain.PlanPerson;
import cn.com.ctrl.yjjy.project.basis.planPerson.service.IPlanPersonService;
import cn.com.ctrl.yjjy.project.system.user.domain.User;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
import cn.com.ctrl.yjjy.project.system.user.service.IUserService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预案分组人员 信息操作处理
 *
 * @author yjjy
 * @date 2019-04-16
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/planPerson")
public class PlanPersonController extends BaseController {
    private String prefix = "basis/planPerson" ;

    @Autowired
    private IPlanPersonService planPersonService;
    @Autowired
    private IPlanGroupService planGroupService;
    @Autowired
    private IPlanService planService;
    @Autowired
    private IUserService userService;

    @RequiresPermissions("basis:group:view")
    @GetMapping("/list/{groupId}")
    public String planPerson(@PathVariable("groupId") String groupId, ModelMap mmap) {
        mmap.put("groupId",groupId);
        return prefix + "/planPerson" ;
    }
    @RequiresPermissions("basis:group:view")
    @GetMapping("/openWord/{groupId}")
    public String openWord(@PathVariable("groupId") String groupId, ModelMap mmap) {
        mmap.put("id",groupId);
        mmap.put("word",planGroupService.selectPlanGroupById(groupId).getWord());
        return prefix + "/openWord";
    }
    /**
     * 保存文档
     */
    @Log(title = "保存文档" , businessType = BusinessType.UPDATE)
    @PostMapping("/upword")
    @ResponseBody
    public AjaxResult upwordSave(PlanGroup planGroup) {
        //更新文档
        return toAjax(planGroupService.updatePlanGroup(planGroup));
    }
    /**
     * 查询预案分组人员列表
     */
    @RequiresPermissions("basis:group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PlanPerson planPerson) {
        startPage();
        List<PlanPerson> list = planPersonService.selectPlanPersonList(planPerson);
        return getDataTable(list);
    }


    /**
     * 导出预案分组人员列表
     */
    @RequiresPermissions("basis:group:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PlanPerson planPerson) {
        List<PlanPerson> list = planPersonService.selectPlanPersonList(planPerson);
        ExcelUtil<PlanPerson> util = new ExcelUtil<PlanPerson>(PlanPerson. class);
        return util.exportExcel(list, "planPerson");
    }

    /**
     * 新增预案分组人员
     */
    @GetMapping("/add/{groupId}")
    public String add(@PathVariable("groupId") String groupId, ModelMap mmap) {
        User user = new User();
        List<User> userList = userService.selectUserList(user);
        mmap.put("userList",userList);
        PlanGroup planGroup = planGroupService.selectPlanGroupById(groupId);
        mmap.put("groupId",groupId);
        mmap.put("groupName",planGroup.getName());
        mmap.put("planName",planGroup.getPlanName());
        return prefix + "/add" ;
    }

    /**
     * 新增保存预案分组人员
     */
    @RequiresPermissions("basis:group:add")
    @Log(title = "预案分组人员" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PlanPerson planPerson) {
        return toAjax(planPersonService.insertPlanPerson(planPerson));
    }

    /**
     * 修改预案分组人员
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        PlanPerson planPerson =planPersonService.selectPlanPersonById(id);
        mmap.put("planPerson" , planPerson);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存预案分组人员
     */
    @RequiresPermissions("basis:group:edit")
    @Log(title = "预案分组人员" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PlanPerson planPerson) {
        return toAjax(planPersonService.updatePlanPerson(planPerson));
    }

    /**
     * 删除预案分组人员
     */
    @RequiresPermissions("basis:group:remove")
    @Log(title = "预案分组人员" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(planPersonService.deletePlanPersonByIds(ids));
    }

}
