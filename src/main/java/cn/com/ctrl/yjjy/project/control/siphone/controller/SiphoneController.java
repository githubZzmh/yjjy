package cn.com.ctrl.yjjy.project.control.siphone.controller;
import cn.com.ctrl.yjjy.common.utils.GainStatus;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.control.siphone.service.SiphoneService;
import cn.com.ctrl.yjjy.project.tool.Numbers;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

/**
 * sip电话操作处理
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Controller
@RequestMapping("/control/siphone")
public class SiphoneController extends BaseController {
    private String prefix = "/control/siphone";
    @Autowired
    private SiphoneService siphoneService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @RequiresPermissions("control:siphone:view")
    @GetMapping()
    public String shebei(ModelMap mmap) {
        return prefix + "/view" ;
    }
    /**
     * 查询设备列表
     */
    @RequiresPermissions("control:siphone:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShebeiCatShebei shebeiCatShebei) {
        startPage();
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        return getDataTable(list);
    }
    /**
     * 登录
     */
    @RequiresPermissions("control:siphone:in")
    @PostMapping("/in")
    @ResponseBody
    public AjaxResult in(String ids) {
        return toAjax(siphoneService.updateShebeiByIds(ids));
    }
    /**
     * 注销
     */
    @RequiresPermissions("control:siphone:out")
    @PostMapping("/out")
    @ResponseBody
    public AjaxResult out(String ids) {
        return toAjax(siphoneService.updateShebeiByIds(ids));
    }
    /**
     * 拨号
     */
    @RequiresPermissions("control:siphone:dial")
    @PostMapping("/dial")
    @ResponseBody
    public AjaxResult dial(String ids) {
        //查询设备状态
        Map maps = GainStatus.getStatus(ids);
        if(maps.containsKey("resultStatus") && !"1".equals(maps.get("resultStatus").toString())){
            return AjaxResult.error(maps.get("msg").toString());
        }
        return toAjax(siphoneService.updateShebeiByIds(ids));
    }
    /**
     * 挂断
     */
    @RequiresPermissions("control:siphone:hang")
    @PostMapping("/hang")
    @ResponseBody
    public AjaxResult hang(String ids) {
        return toAjax(siphoneService.updateShebeiByIds(ids));
    }
    /**
     * 保持
     */
    @RequiresPermissions("control:siphone:keep")
    @PostMapping("/keep")
    @ResponseBody
    public AjaxResult keep(String ids) {
        return toAjax(siphoneService.updateShebeiByIds(ids));
    }
    /**
     * 应答
     */
    @RequiresPermissions("control:siphone:counter")
    @PostMapping("/counter")
    @ResponseBody
    public AjaxResult counter(String ids) {
        return toAjax(siphoneService.updateShebeiByIds(ids));
    }
    /**
     * 转接
     */
    @RequiresPermissions("control:siphone:transfer")
    @PostMapping("/transfer")
    @ResponseBody
    public AjaxResult transfer(String ids) {
        return toAjax(siphoneService.updateShebeiByIds(ids));
    }
    /**
     * 信息发送
     */
    @RequiresPermissions("control:siphone:message")
    @PostMapping("/message")
    @ResponseBody
    public AjaxResult message(String ids) {
        String msg = "短信发送："+ids;
        AjaxResult json = new AjaxResult();
        json.put("msg" , msg);
        return json;
    }
}