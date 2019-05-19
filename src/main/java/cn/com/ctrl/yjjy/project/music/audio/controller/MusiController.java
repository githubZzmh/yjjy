package cn.com.ctrl.yjjy.project.music.audio.controller;

import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
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
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 音乐 信息操作处理
 *
 * @author zzmh
 * @date 2018-12-03
 */
@Data
@Log4j2
@Controller
@RequestMapping("/music/audio")
public class MusiController extends BaseController {
    private String prefix = "music/audio" ;

    @Autowired
    private IMusiService musicService;
    @Autowired
    private IShebeiService shebeiService;

    @RequiresPermissions("music:audio:view")
    @GetMapping()
    public String music() {
        return prefix + "/music" ;
    }

    /**
     * 查询音乐列表
     */
    @RequiresPermissions("music:audio:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shebei shebei) {
        startPage();
//        List<Music> list = musicService.selectMusicList(music);
        List<Shebei> list = shebeiService.selectShebeiList(shebei);
        return getDataTable(list);
    }

    @RequiresPermissions("music:audio:list")
    @PostMapping("/all")
    @ResponseBody
    public AjaxResult all(Music music) {
        //处理接口
        List<Shebei> ls = shebeiService.selectShebeiList(null);
        List<Music> list = musicService.selectMusicList(null);
        List<String> lstring = new ArrayList<String>();
        List<String> lstring2 = new ArrayList<String>();
        for (Shebei s:ls){
            lstring.add(s.getId());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long etime1=System.currentTimeMillis()+1*60*1000;
        String b = sdf.format(etime1);
        String e = "23:59";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", lstring);
        for (Music s:list){
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


    @RequiresPermissions("music:audio:list")
    @PostMapping("/stop")
    @ResponseBody
    public AjaxResult stop(Music music) {
        //处理接口
        List<Shebei> ls = shebeiService.selectShebeiList(null);
        List<Music> list = musicService.selectMusicList(null);
        List<String> lstring = new ArrayList<String>();
        List<String> lstring2 = new ArrayList<String>();
        for (Shebei s:ls){
            lstring.add(s.getId());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long etime1=System.currentTimeMillis()+1*60*1000;
        String b = "00:00";//sdf.format(etime1);
        String e = "00:00";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", lstring);
        for (Music s:list){
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