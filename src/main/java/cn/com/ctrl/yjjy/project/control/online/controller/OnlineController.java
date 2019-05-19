package cn.com.ctrl.yjjy.project.control.online.controller;

import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.control.conelrad.service.ConelradService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线广播操作处理
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Controller
@RequestMapping("/control/online")
public class OnlineController extends BaseController {
    private String prefix = "/control/online";
    @Autowired
    private ConelradService conelradService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @RequiresPermissions("control:online:view")
    @GetMapping()
    public String shebei(ModelMap mmap) {
        return prefix + "/view" ;
    }
    /**
     * 查询设备列表
     */
    @RequiresPermissions("control:online:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShebeiCatShebei shebeiCatShebei) {
        startPage();
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        return getDataTable(list);
    }
    /**
     * 播放
     */
    @RequiresPermissions("control:online:play")
    @PostMapping("/play")
    @ResponseBody
    public AjaxResult play(String ids) {
        String[] aids = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        //机器ids
        map.put("hostIds", aids);
        //广播标识 1开始 0结束
        map.put("broadStatus", "1");
        //1在线 0紧急
        map.put("broadOnline", "1");
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost("http://47.52.172.172:8081/yjjy/tcp/broad",json);//("http://192.168.4.101:8080/yjjy/tcp/broad", json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        if (!"任务已下发".equals(msg)) {
            return toAjax(-1);
        }
        return toAjax(1);
    }
    /**
     * 停止
     */
    @RequiresPermissions("control:online:stop")
    @PostMapping("/stop")
    @ResponseBody
    public AjaxResult stop(String ids) {
        String[] aids = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        //机器ids
        map.put("hostIds", aids);
        //广播标识 1开始 0结束
        map.put("broadStatus", "0");
        //1在线 0紧急
        map.put("broadOnline", "1");
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost("http://47.52.172.172:8081/yjjy/tcp/broad",json);//("http://192.168.4.101:8080/yjjy/tcp/broad", json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        if (!"任务已下发".equals(msg)) {
            return toAjax(-1);
        }
        return toAjax(1);
    }
}