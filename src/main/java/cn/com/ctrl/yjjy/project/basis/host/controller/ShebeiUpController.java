package cn.com.ctrl.yjjy.project.basis.host.controller;

import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 设备升级 信息操作处理
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/hostup")
public class ShebeiUpController extends BaseController {
    private String prefix = "basis/hostup" ;
    @Autowired
    private IShebeiService shebeiService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatService;
    @RequiresPermissions("basis:host:view")
    @GetMapping()
    public String shebei() {
        return prefix + "/shebei" ;
    }
    /**
     * 查询设备列表
     */
    @RequiresPermissions("basis:host:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shebei shebei) {
        startPage();
        List<Shebei> list = shebeiService.selectShebeiList(shebei);
        return getDataTable(list);
    }
    /**
     * 升级设备列表
     */
    @RequiresPermissions("basis:host:outdown")
    @PostMapping("/outdown")
    @ResponseBody
    public AjaxResult outdown(String ids) {
        Shebei shebei = shebeiService.selectShebeiById(ids);
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> ls = new ArrayList<String>();
        ls.add("a607a243-3430-4443-bb48-0fbceee5407f");
        map.put("hostIds", ls);
        map.put("upgradeId", "b67ca003-f3f5-4bbf-b41a-a6133ad99912");
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "hostUp",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        if (!"任务已下发".equals(msg)) {
            return toAjax(-1);
        }
        return toAjax(shebeiService.updateShebeiByIds(ids));
    }

    /**
     * 新增设备
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }
    /**
     * 新增保存设备
     */
    @RequiresPermissions("basis:host:add")
    @Log(title = "设备" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Shebei shebei) {
        String uuid = UUID.randomUUID().toString();
        shebei.setId(uuid);
        shebei.setSynchronization("0");
        shebei.setCreatetime(new Date());
        return toAjax(shebeiService.insertShebei(shebei));
    }
    /**
     * 修改设备
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Shebei shebei =shebeiService.selectShebeiById(id);
        mmap.put("shebei" , shebei);
        return prefix + "/edit" ;
    }
    /**
     * 修改保存设备
     */
    //
    @RequiresPermissions("basis:host:edit")
    @Log(title = "设备" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Shebei shebei) {
        shebei.setSynchronization("0");
        shebeiService.updateShebei(shebei);
        ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
        shebeiCatShebei.setShebeiId(shebei.getId());
        List<ShebeiCatShebei>  lscs = shebeiCatService.selectShebeiCatShebeiList(shebeiCatShebei);
        HashMap map = new HashMap<String, Object>();
        //设备id
        map.put("hostId", shebei.getId());
        //主机号
        map.put("hostNum", shebei.getNumId());
        //ip地址
        map.put("hostIp", shebei.getIp());
        //网关
        map.put("gateway", shebei.getGateway());
        //子网掩码
        map.put("subnet_mask", shebei.getMask());
        //分组地址Id
        map.put("catId", lscs.get(0).getId());
        //sipIP地址
        map.put("sipIp", shebei.getSipIp());
        //端口号
        map.put("sipPort", shebei.getSipPort());
        //SIP密码
        map.put("sipPwd", shebei.getSipPwd());
        //sip号
        map.put("sipId", shebei.getSipId());
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "host",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        AjaxResult ajaxResult = new AjaxResult();
        if (!"任务已下发".equals(msg)) {
            ajaxResult.put("bool","false");
        } else {
            ajaxResult.put("bool","true");
        }
        return ajaxResult;
        //return toAjax();
    }
    /**
     * 删除设备
     */
    @RequiresPermissions("basis:host:remove")
    @Log(title = "设备" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(shebeiService.deleteShebeiByIds(ids));
    }
}