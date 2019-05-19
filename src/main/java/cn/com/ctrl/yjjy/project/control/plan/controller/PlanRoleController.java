package cn.com.ctrl.yjjy.project.control.plan.controller;
import cn.com.ctrl.yjjy.common.utils.StringUtils;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.control.plan.domain.PlanListView;
import cn.com.ctrl.yjjy.project.control.plan.domain.PlanRole;
import cn.com.ctrl.yjjy.project.control.plan.service.IPlanRoleService;
import cn.com.ctrl.yjjy.project.system.role.service.IRoleService;
import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * 角色预案 信息操作处理
 *
 * @author yjjy
 * @date 2018-12-25
 */
@Data
@Log4j2
@Controller
@RequestMapping("/control/planRole")
public class PlanRoleController extends BaseController {
    private String prefix = "control/planRole" ;
    @Autowired
    private IPlanRoleService planRoleService;
    @Autowired
    private IPlanService planService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    Environment environment;

    @RequiresPermissions("control:plan:view")
    @GetMapping()
    public String planRole() {
        return prefix + "/planRole" ;
    }
    /**
     * 查询角色预案列表
     */
    @RequiresPermissions("control:plan:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PlanListView pv) {
        startPage();
        List<PlanRole> list = planRoleService.selectPlanRoleList(pv);
        List<PlanListView> lplv = new ArrayList<PlanListView>();
        for (PlanRole planRole: list){
            PlanListView plv = new PlanListView();
            plv.setId(planRole.getId());
            plv.setPlanId(planRole.getPlanId());
            plv.setRoleId(planRole.getRoleId());
            plv.setPlanName(planRole.getPlan().getName());
            plv.setRoleName(planRole.getRole().getRoleName());
            plv.setName(planRole.getName());
            plv.setUrl(planRole.getUrl());
            lplv.add(plv);
        }
        return getDataTable(lplv);
    }
    /**
     * 新增预案
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<Plan> lPlan = planService.selectPlanList(null);
        mmap.put("lPlan", lPlan);
//        List<Role> lRole = roleService.selectRoleBy(null);
//        mmap.put("lRole", lRole);
        return prefix + "/add" ;
    }
    /**
     * 新增保存预案
     */
    @RequiresPermissions("control:plan:add")
    @Log(title = "预案" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PlanRole planRole) throws IOException {
        String outUrl = "D:\\fakepath\\out\\" + planRole.getUrl();
        File file=new File(outUrl);
        if(!file.exists()){
            file.createNewFile();
        }
        FileInputStream fis=new FileInputStream("D:\\fakepath\\" + planRole.getUrl());
        FileOutputStream fos=new FileOutputStream(outUrl);
        BufferedInputStream bis=new BufferedInputStream(fis);
        BufferedOutputStream bos=new BufferedOutputStream(fos);
        int b;
        while((b=fis.read())!=-1){
            bos.write(b);
        }
        bis.close();
        bos.close();
        String uuid = UUID.randomUUID().toString();
        planRole.setId(uuid);
        planRole.setUrl(outUrl);
        return toAjax(planRoleService.insertPlanRole(planRole));
    }
    /**
     * 修改角色预案
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        PlanRole planRole =planRoleService.selectPlanRoleById(id);
        PlanListView plv = new PlanListView();
        plv.setId(planRole.getId());
        plv.setPlanId(planRole.getPlanId());
        plv.setRoleId(planRole.getRoleId());
        plv.setPlanName(planRole.getPlan().getName());
        plv.setRoleName(planRole.getRole().getRoleName());
        plv.setName(planRole.getName());
        plv.setUrl(planRole.getUrl());
        mmap.put("plv" , plv);
        List<Plan> lPlan = planService.selectPlanList(null);
        mmap.put("lPlan", lPlan);
        return prefix + "/edit" ;
    }
    /**
     * 修改保存预案
     */
    @RequiresPermissions("control:plan:edit")
    @Log(title = "角色预案" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PlanRole planRole) {
        return toAjax(planRoleService.updatePlanRole(planRole));
    }
    /**
     * 删除角色预案
     */
    @RequiresPermissions("control:plan:edit")
    @Log(title = "角色预案" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(planRoleService.deletePlanRoleByIds(ids));
    }


    @RequestMapping("imgSave")
    @ResponseBody
    public Map<String,Object> imgSave(@RequestParam("file") MultipartFile file)  {
//        String filename = file.getOriginalFilename();
        Map<String,Object> map = new HashMap<>();
        String url = uploadFile(file);
        map.put("url",url);
        return map;
    }

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file){
        // 判断文件是否为空
        String filePath = "";
        String filename = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                //判断文件目录是否存在，否则自动生成
                File directory = new File(environment.getProperty("upload"));
                if (!directory.exists()){
                    directory.mkdirs();
                }

                //失败跳转视图
//        if (file.getSize() > 30000)
//          return new ModelAndView("uploadFail","msg",file.getOriginalFilename()+"超过了指定大小");

                //获取图片扩展名
                String ext = filename.substring(filename.lastIndexOf("."));
                filename = StringUtils.gainGUID() +ext;
                // 文件保存路径
                filePath =  FilenameUtils.concat(environment.getProperty("upload"), filename);
                // 转存文件
                file.transferTo(new File(filePath));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //成功跳转视图
//    return new ModelAndView("uploadSuccess","msg",file.getOriginalFilename());
        return environment.getProperty("fileupload.directory")+filename;
    }
}