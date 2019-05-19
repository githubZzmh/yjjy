package cn.com.ctrl.yjjy.project.broadcast.group.controller;

import cn.com.ctrl.yjjy.common.utils.GainStatus;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatService;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
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
@RequestMapping("/broadcast/group")
public class BroGroupController extends BaseController {
    private String prefix = "broadcast";
    @Autowired
    private IMusiService musicService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @Autowired
    private IShebeiCatService shebeiCatService;
    @Autowired
    private IShebeiService shebeiService;
    @RequiresPermissions("broadcast:group:view")
    @GetMapping()
    public String view() {
        return prefix + "/broadcastGroup";
    }
    @RequiresPermissions("broadcast:group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShebeiCat shebeiCat) {
        startPage();
        List<ShebeiCat> list = shebeiCatService.selectShebeiCatList(shebeiCat);
        return getDataTable(list);
    }
    @RequiresPermissions("broadcast:group:list")
    @PostMapping("/openyy")
    @ResponseBody
    public AjaxResult openyy(String catId) {
        ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
        shebeiCatShebei.setCatId(catId);
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        //查询所有的设备id
        List<String> idList = new ArrayList<>();
        list.forEach((shebeiCatSb) -> idList.add(shebeiCatSb.getShebeiId()));
        int num = 0;
        for(String id:idList){
            //查询设备状态   没有存在离线不放的设备时，则全部播放
            Map maps = GainStatus.getStatus(id);
            if(maps.containsKey("resultStatus") && !"3".equals(maps.get("resultStatus").toString())){
                break;
            }else {
                num++;
            }
        }
        if(num == idList.size()){
            return AjaxResult.error("所有设备处于播放状态！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Shebeit sb = new Shebeit();
        sb.setIP(list.get(0).getShebeiCat().getIp());
        sb.setPort("12346");
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        for (ShebeiCatShebei scs: list) {
            Shebeit sbb = new Shebeit();
            sbb.setIP(scs.getShebei().getIp());
            sbb.setPort(scs.getShebei().getPort().toString());
            lsb.add(sbb);
        }
        Catt catt = new Catt();
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
    @RequiresPermissions("broadcast:group:list")
    @PostMapping("/endyy")
    @ResponseBody
    public AjaxResult endyy(String catId) {
        ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
        shebeiCatShebei.setCatId(catId);
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        //查询所有的设备id
        List<String> idList = new ArrayList<>();
        list.forEach((shebeiCatSb) -> idList.add(shebeiCatSb.getShebeiId()));
        int num = 0;
        for(String id:idList){
            //查询设备状态   没有存在离线不放的设备时，则全部播放
            Map maps = GainStatus.getStatus(id);
            if(maps.containsKey("resultStatus") && "3".equals(maps.get("resultStatus").toString())){
                break;
            }else {
                num++;
            }
        }
        if(num == idList.size()){
            return AjaxResult.error("所有设备处于停止状态！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Shebeit sb = new Shebeit();
        sb.setIP(list.get(0).getShebeiCat().getIp());
        sb.setPort("12346");
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        for (ShebeiCatShebei scs: list) {
            Shebeit sbb = new Shebeit();
            sbb.setIP(scs.getShebei().getIp());
            sbb.setPort(scs.getShebei().getPort().toString());
            lsb.add(sbb);
        }
        Catt catt = new Catt();
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
    @RequiresPermissions("broadcast:group:list")
    @PostMapping("/openhh")
    @ResponseBody
    public AjaxResult openhh(String catId) {
        ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
        shebeiCatShebei.setCatId(catId);
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        //查询所有的设备id
        List<String> idList = new ArrayList<>();
        list.forEach((shebeiCatSb) -> idList.add(shebeiCatSb.getShebeiId()));
        int num = 0;
        for(String id:idList){
            //查询设备状态   没有存在离线不放的设备时，则全部播放
            Map maps = GainStatus.getStatus(id);
            if(maps.containsKey("resultStatus") && !"5".equals(maps.get("resultStatus").toString())){
                break;
            }else {
                num++;
            }
        }
        if(num == idList.size()){
            return AjaxResult.error("所有设备处于播放状态！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Shebeit sb = new Shebeit();
        sb.setIP(list.get(0).getShebeiCat().getIp());
        sb.setPort("12346");
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        for (ShebeiCatShebei scs: list) {
            Shebeit sbb = new Shebeit();
            sbb.setIP(scs.getShebei().getIp());
            sbb.setPort(scs.getShebei().getPort().toString());
            lsb.add(sbb);
        }
        Catt catt = new Catt();
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
    @RequiresPermissions("broadcast:group:list")
    @PostMapping("/endhh")
    @ResponseBody
    public AjaxResult endhh(String catId) {
        ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
        shebeiCatShebei.setCatId(catId);
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        //查询所有的设备id
        List<String> idList = new ArrayList<>();
        list.forEach((shebeiCatSb) -> idList.add(shebeiCatSb.getShebeiId()));
        int num = 0;
        for(String id:idList){
            //查询设备状态   没有存在离线不放的设备时，则全部播放
            Map maps = GainStatus.getStatus(id);
            if(maps.containsKey("resultStatus") && "5".equals(maps.get("resultStatus").toString())){
                break;
            }else {
                num++;
            }
        }
        if(num == idList.size()){
            return AjaxResult.error("所有设备处于停止状态！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Shebeit sb = new Shebeit();
        sb.setIP(list.get(0).getShebeiCat().getIp());
        sb.setPort("12346");
        List<Shebeit> lsb = new ArrayList<Shebeit>();
        for (ShebeiCatShebei scs: list) {
            Shebeit sbb = new Shebeit();
            sbb.setIP(scs.getShebei().getIp());
            sbb.setPort(scs.getShebei().getPort().toString());
            lsb.add(sbb);
        }
        Catt catt = new Catt();
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
}