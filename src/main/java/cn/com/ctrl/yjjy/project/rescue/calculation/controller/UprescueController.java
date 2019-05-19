package cn.com.ctrl.yjjy.project.rescue.calculation.controller;

import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rescue/uprescue")
public class UprescueController extends BaseController {
    @RequiresPermissions("rescue:uprescue:view")
    @GetMapping()
    public String Uprescue(ModelMap mmap) {
        return "rescue/uprescue/up";
    }
}