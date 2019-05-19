package cn.com.ctrl.yjjy.project.music.audio.controller;

import cn.com.ctrl.yjjy.common.utils.GainStatus;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.music.audio.service.IMusiService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Log4j2
@Controller
@RequestMapping("/music/time")
public class PTimeController extends BaseController {

    private String prefix = "music/audio" ;
    @Autowired
    private IShebeiService shebeiService;
    @Autowired
    private IMusiService musicService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatService;

    @RequiresPermissions("music:time:view")
    @GetMapping()
    public String dingshi() {
        return prefix + "/dingshi" ;
    }

    /**
     * 查询设备列表
     */
    @RequiresPermissions("music:time:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shebei shebei) {
        startPage();
        List<Shebei> list = shebeiService.selectShebeiList(shebei);
        return getDataTable(list);
    }

    @RequiresPermissions("music:time:list")
    @PostMapping("/starttime")
    @ResponseBody
    public AjaxResult start(String ids, String start, String stop) {
        //查询设备状态
        Map maps = GainStatus.getStatus(ids);
        if(maps.containsKey("resultStatus") && !"1".equals(maps.get("resultStatus").toString())){
            return AjaxResult.error(maps.get("msg").toString());
        }
        //根据groupId获取设备信息
        List<Music> list1 = musicService.selectMusicList(null);
        List<String> li = new ArrayList<String>();
        String dd [] = ids.split(",");
        for(int i =0;i<dd.length;i++){
            li.add(dd[i]);
        }
        List<String> lstring2 = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long etime1=System.currentTimeMillis()+1*60*1000;
        String b = start;
        String e = stop;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", li);
        for (Music s:list1){
            lstring2.add(s.getId());
        }
        map.put("musicIds", lstring2);
        map.put("time", b + e);
        JSONObject json = new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "timBroadCast",json);
        log.info(data);
        return AjaxResult.success();
    }


    @RequiresPermissions("music:time:list")
    @PostMapping("/stoptime")
    @ResponseBody
    public AjaxResult stop(String ids,String start,String stop) {
        //查询设备状态
        Map maps = GainStatus.getStatus(ids);
        if(maps.containsKey("resultStatus") && !"2".equals(maps.get("resultStatus").toString())){
            return AjaxResult.error(maps.get("msg").toString());
        }

        //根据groupId获取设备信息
        List<Music> list1 = musicService.selectMusicList(null);
        List<String> li = new ArrayList<String>();
        List<String> lstring2 = new ArrayList<String>();
        String dd [] = ids.split(",");
        for(int i =0;i<dd.length;i++){
            li.add(dd[i]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long etime1=System.currentTimeMillis()+1*60*1000;
        String b = start;
        String e = stop;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", li);
        for (Music s:list1){
            lstring2.add(s.getId());
        }
        map.put("musicIds", lstring2);
        map.put("time", b + e);
        JSONObject json = new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "timBroadCast",json);
        log.info(data);
        return AjaxResult.success();
    }
}
