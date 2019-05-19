package cn.com.ctrl.yjjy.project.music.audio.controller;

import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.mapper.ShebeiCatShebeiMapper;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatService;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.music.audio.service.IMusiService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/11.
 */
@Data
@Log4j2
@Controller
@RequestMapping("/music/group")
public class PSheBeiCatcontroller extends BaseController {
    private String prefix = "/music/audio" ;
    @Autowired
    private IShebeiCatService shebeiCatService;
    @Autowired
    private ShebeiCatShebeiMapper shebeiCatShebeiMapper;
    @Autowired
    private IMusiService musicService;
    @RequiresPermissions("music:group:view")
    @GetMapping()
    public String shebeiCat() {
        return prefix + "/shebeiCat" ;
    }
    /**
     * 查询设备分组列表
     */
    @RequiresPermissions("music:group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShebeiCat shebeiCat) {
        startPage();
        List<ShebeiCat> list = shebeiCatService.selectShebeiCatList(shebeiCat);
        return getDataTable(list);
    }
    @RequiresPermissions("music:group:list")
    @PostMapping("/start")
    @ResponseBody
    public AjaxResult start(String ids) {
        //根据groupId获取设备信息
        List<ShebeiCatShebei> list =shebeiCatShebeiMapper.selectByList(ids);
        List<Music> list1 = musicService.selectMusicList(null);
        List<String> li = new ArrayList<String>();
        List<String> lstring2 = new ArrayList<String>();
        for(int i =0;i<list.size();i++){
            li.add(list.get(i).getShebeiId());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long etime1=System.currentTimeMillis()+1*60*1000;
        String b = sdf.format(etime1);
        String e = "23:59";
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
    @RequiresPermissions("music:group:list")
    @PostMapping("/stop")
    @ResponseBody
    public AjaxResult stop(String ids) {
        //根据groupId获取设备信息
        List<ShebeiCatShebei> list =shebeiCatShebeiMapper.selectByList(ids);
        List<Music> list1 = musicService.selectMusicList(null);
        List<String> li = new ArrayList<String>();
        List<String> lstring2 = new ArrayList<String>();
        for(int i =0;i<list.size();i++){
            li.add(list.get(i).getShebeiId());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long etime1=System.currentTimeMillis()+1*60*1000;
        String b = "00:00";//sdf.format(etime1);
        String e = "00:00";
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
    /**
     * 查询设备列表
     */
    @RequiresPermissions("music:group:list")
    @GetMapping("/shebeiList/{catId}")
    public String list(@PathVariable("catId") String  catId, ModelMap mmap) {
        ShebeiCat shebeiCat = shebeiCatService.selectShebeiCatById(catId);
        List<ShebeiCat> listShebeiCat = shebeiCatService.selectShebeiCatList(null);
        mmap.put("shebeiCat" , shebeiCat);
        mmap.put("listShebeiCat" , listShebeiCat);
        return prefix + "/shebeiList";
    }
}
