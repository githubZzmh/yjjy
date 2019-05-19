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
 * 设备 信息操作处理
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/host")
public class ShebeiController extends BaseController {
    private String prefix = "basis/host" ;
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
     * 发送设备列表
     */
    @RequiresPermissions("basis:host:list")
    @PostMapping("/outdown")
    @ResponseBody
    public AjaxResult outdown(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostId", ids);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "tcpHeat",json);
        log.info(data);
        if ("".equals(data)) {
            return toAjax(0);
        }
        json = JSONObject.parseObject(data);
        String status = json.get("status").toString();
        if (!"0".equals(status)) {
            return toAjax(0);
        }
        return toAjax(1);
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
        int i = shebeiService.insertShebei(shebei);
        HashMap map = new HashMap<String, Object>();
        //机器id
        map.put("hostId", shebei.getId());
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "tcpHeat",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        //设备在线
        if ("任务已下发".equals(msg)) {
            shebeiService.updateShebeiByIds(shebei.getId());
            ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
            shebeiCatShebei.setShebeiId(shebei.getId());
            List<ShebeiCatShebei>  lscs = shebeiCatService.selectShebeiCatShebeiList(shebeiCatShebei);
            map = new HashMap<String, Object>();
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
            map.put("catId", lscs.get(0).getCatId());
            //sipIP地址
            map.put("sipIp", shebei.getSipIp());
            //端口号
            map.put("sipPort", shebei.getSipPort());
            //SIP密码
            map.put("sipPwd", shebei.getSipPwd());
            //sip号
            map.put("sipId", shebei.getSipId());
            json =new JSONObject(map);
            log.info(json);
            //更改主机
            data = HttpUtils.sendPost(MyStatic.url + "host",json);
            log.info(data);
            json = JSONObject.parseObject(data);
            msg = json.get("msg").toString();
            if ("任务已下发".equals(msg)) {
                List<String> ls = new ArrayList<String>();
                ls.add(shebei.getId());
                json =new JSONObject(map);
                map = new HashMap<String, Object>();
                Object idsJSON = JSONArray.toJSON(ls);
                map.put("hostIds", idsJSON);
                map.put("volume", shebei.getVolume());
                json =new JSONObject(map);
                log.info(json);
                data = HttpUtils.sendPost(MyStatic.url + "volume",json);
                log.info(data);
                json = JSONObject.parseObject(data);
                msg = json.get("msg").toString();
                if (!"任务已下发".equals(msg)) {
                    return toAjax(0);
                }
            }
        }
        return toAjax(i);
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
        map.put("catId", lscs.get(0).getCatId());
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
        if ("任务已下发".equals(msg)) {
            shebeiService.updateShebeiByIds(shebei.getId());
            List<String> ls = new ArrayList<String>();
            ls.add(shebei.getId());
            json =new JSONObject(map);
            map = new HashMap<String, Object>();
            Object idsJSON = JSONArray.toJSON(ls);
            map.put("hostIds", idsJSON);
            map.put("volume", shebei.getVolume());
            json =new JSONObject(map);
            log.info(json);
            data = HttpUtils.sendPost(MyStatic.url + "volume",json);
            log.info(data);
            json = JSONObject.parseObject(data);
            msg = json.get("msg").toString();
            if (!"任务已下发".equals(msg)) {
                return toAjax(0);
            }
            return success("修改设备成功");
        }
        return error();
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
    /**
     * 设备重启
     */
    @RequiresPermissions("basis:host:list")
    @PostMapping("/restart")
    @ResponseBody
    public AjaxResult restart(String shebeId) {
        List<String> ls = new ArrayList<String>();
        ls.add(shebeId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostId", shebeId);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "reStart",json);
        log.info(data);
        if ("".equals(data)) {
            return toAjax(0);
        }
        json = JSONObject.parseObject(data);
        String status = json.get("status").toString();
        if (!"0".equals(status)) {
            return toAjax(0);
        }
        return toAjax(1);
    }
}