package cn.com.ctrl.yjjy.project.broadcast.point.controller;

import cn.com.ctrl.yjjy.common.utils.GainStatus;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.broadcast.group.domain.Catt;
import cn.com.ctrl.yjjy.project.broadcast.group.domain.Shebeit;
import cn.com.ctrl.yjjy.project.music.audio.service.IMusiService;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分组广播操作处理
 *
 * @author zzmh
 * @date 2018-12-03
 */
@Data
@Log4j2
@Controller
@RequestMapping("/broadcast/point")
public class BroPointController extends BaseController {
    private String prefix = "broadcast";
    @Autowired
    private IMusiService musicService;
    @Autowired
    private IShebeiService shebeiService;
    @RequiresPermissions("broadcast:point:view")
    @GetMapping()
    public String view() {
        return prefix + "/broadcastPoint";
    }
    @RequiresPermissions("broadcast:point:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shebei shebei) {
        startPage();
        List<Shebei> list = shebeiService.selectShebeiList(shebei);
        return getDataTable(list);
    }
    @RequiresPermissions("broadcast:point:list")
    @PostMapping("/openhh")
    @ResponseBody
    public AjaxResult openhh(String id) {
        //查询设备状态
        Map maps = GainStatus.getStatus(id);
        if(maps.containsKey("resultStatus") && "5".equals(maps.get("resultStatus").toString())){
            return AjaxResult.error(maps.get("msg").toString());
        }
        Shebei shebei = shebeiService.selectShebeiById(id);
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        Shebeit sbb = new Shebeit();
        sbb.setIP(shebei.getIp());
        sbb.setPort(shebei.getPort().toString());
        lsb.add(sbb);
        Map<String, Object> map = new HashMap<String, Object>();
        Catt catt = new Catt();
        Shebeit sb = new Shebeit();
        sb.setIP(shebei.getIp());
        sb.setPort("12346");
        catt.setGroupIP(sb);
        catt.setIPModelList(lsb);
        List<Catt> lc = new ArrayList<Catt>();
        lc.add(catt);
        map.put("IPList", lc);
        map.put("OperationUser", "");
        map.put("OperationType", 1);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost2("http://localhost:8055/api/default/StartSpeaking",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        Boolean b = (Boolean) json.get("Success");
        if (b) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
    @RequiresPermissions("broadcast:point:list")
    @PostMapping("/endhh")
    @ResponseBody
    public AjaxResult endhh(String id) {Shebei shebei = shebeiService.selectShebeiById(id);
        //查询设备状态
        Map maps = GainStatus.getStatus(id);
        if(maps.containsKey("resultStatus") && !"5".equals(maps.get("resultStatus").toString())){
            return AjaxResult.error(maps.get("msg").toString());
        }
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        Shebeit sbb = new Shebeit();
        sbb.setIP(shebei.getIp());
        sbb.setPort(shebei.getPort().toString());
        lsb.add(sbb);
        Map<String, Object> map = new HashMap<String, Object>();
        Catt catt = new Catt();
        Shebeit sb = new Shebeit();
        sb.setIP(shebei.getIp());
        sb.setPort("12346");
        catt.setGroupIP(sb);
        catt.setIPModelList(lsb);
        List<Catt> lc = new ArrayList<Catt>();
        lc.add(catt);
        map.put("IPList", lc);
        map.put("OperationUser", "");
        map.put("OperationType", 1);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost2("http://localhost:8055/api/default/StopSpeaking",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        Boolean b = (Boolean) json.get("Success");
        if (b) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
    @RequiresPermissions("broadcast:point:list")
    @PostMapping("/openyy")
    @ResponseBody
    public AjaxResult openyy(String id) {
        //查询设备状态
        Map maps = GainStatus.getStatus(id);
        if(maps.containsKey("resultStatus") && "3".equals(maps.get("resultStatus").toString())){
            return AjaxResult.error(maps.get("msg").toString());
        }
        Shebei shebei = shebeiService.selectShebeiById(id);
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        Shebeit sbb = new Shebeit();
        sbb.setIP(shebei.getIp());
        sbb.setPort(shebei.getPort().toString());
        lsb.add(sbb);
        Map<String, Object> map = new HashMap<String, Object>();
        Catt catt = new Catt();
        Shebeit sb = new Shebeit();
        sb.setIP(shebei.getIp());
        sb.setPort("12346");
        catt.setGroupIP(sb);
        catt.setIPModelList(lsb);
        List<Catt> lc = new ArrayList<Catt>();
        lc.add(catt);
        map.put("IPList", lc);
        map.put("OperationUser", "");
        map.put("OperationType", 0);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost2("http://localhost:8055/api/default/StartSpeaking",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        Boolean b = (Boolean) json.get("Success");
        if (b) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
    @RequiresPermissions("broadcast:point:list")
    @PostMapping("/endyy")
    @ResponseBody
    public AjaxResult endyy(String id) {Shebei shebei = shebeiService.selectShebeiById(id);
        //查询设备状态
        Map maps = GainStatus.getStatus(id);
        if(maps.containsKey("resultStatus") && !"3".equals(maps.get("resultStatus").toString())){
            return AjaxResult.error(maps.get("msg").toString());
        }
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        Shebeit sbb = new Shebeit();
        sbb.setIP(shebei.getIp());
        sbb.setPort(shebei.getPort().toString());
        lsb.add(sbb);
        Map<String, Object> map = new HashMap<String, Object>();
        Catt catt = new Catt();
        Shebeit sb = new Shebeit();
        sb.setIP(shebei.getIp());
        sb.setPort("12346");
        catt.setGroupIP(sb);
        catt.setIPModelList(lsb);
        List<Catt> lc = new ArrayList<Catt>();
        lc.add(catt);
        map.put("IPList", lc);
        map.put("OperationUser", "");
        map.put("OperationType", 0);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost2("http://localhost:8055/api/default/StopSpeaking",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        Boolean b = (Boolean) json.get("Success");
        if (b) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}