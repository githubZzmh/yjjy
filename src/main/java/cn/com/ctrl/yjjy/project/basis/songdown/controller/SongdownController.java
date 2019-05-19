package cn.com.ctrl.yjjy.project.basis.songdown.controller;
import cn.com.ctrl.yjjy.common.support.Convert;
import cn.com.ctrl.yjjy.common.utils.JsonUtil;
import cn.com.ctrl.yjjy.common.utils.StringUtils;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.common.utils.security.ShiroUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.audio.service.IMusicService;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.basis.songdown.domain.SMusic;
import cn.com.ctrl.yjjy.project.system.user.domain.User;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
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
@RequestMapping("/basis/songdown")
public class SongdownController extends BaseController {
    private String prefix = "basis/songdown";
    @Autowired
    private IShebeiService shebeiService;
    @Autowired
    private IMusicService musicService;

    @RequiresPermissions("basis:songdown:view")
    @GetMapping()
    public String shebei() {
        return prefix + "/shebei" ;
    }
    /**
     * 查询设备音乐
     */
    @RequiresPermissions("basis:songdown:list")
    @GetMapping("/list/{shebeId}")
    public String detail(@PathVariable("shebeId") String shebeId, ModelMap mmap) {
        mmap.put("shebeId" , shebeId);
        return prefix + "/music" ;
    }
    /**
     * 查询设备列表
     */
    @RequiresPermissions("basis:songdown:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shebei shebei) {
        startPage();
        List<Shebei> list = shebeiService.selectShebeiList(shebei);
        return getDataTable(list);
    }
    /**
     * 设备下发音乐
     */
    @RequiresPermissions("basis:songdown:list")
    @GetMapping("/add/{shebeId}")
    public String add(@PathVariable("shebeId") String shebeId, ModelMap mmap) {
        Shebei shebei = shebeiService.selectShebeiById(shebeId);
        mmap.put("shebeId", shebeId);
        return prefix + "/add";
    }
    /**
     * 查询设备音乐列表
     */
    @RequiresPermissions("basis:songdown:list")
    @PostMapping("/listMusic")
    @ResponseBody
    public TableDataInfo list(Music music, String shebeId) {
        startPage();
        List<Music> list = musicService.selectMusicListByShebeiId("'" + shebeId + "'");
        return getDataTable(list);
    }
    /**
     * 查询库音乐列表
     */
    @RequiresPermissions("basis:songdown:list")
    @PostMapping("/addlistMusic")
    @ResponseBody
    public TableDataInfo addlist(Music music, String shebeId) {
        startPage();
        List<Music> list = musicService.selectMusicListByShebeiId_("'" + shebeId + "'");
        return getDataTable(list);
    }
    /**
     * 歌曲下发
     */
    @RequiresPermissions("basis:songdown:issue")
    @PostMapping("/listadd")
    @ResponseBody
    public AjaxResult listadd(String shebeId, String ids) {
        List<String> ls = new ArrayList<String>();
        ls.add(shebeId);
        String[] aids = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", ls);
        map.put("musicIds", aids);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "music",json);
        log.info(data);
        if ("".equals(data)) {
            return toAjax(0);
        }
        json = JSONObject.parseObject(data);
        String status = json.get("status").toString();
        if (!"0".equals(status)) {
            return toAjax(0);
        }
        for (String s: aids) {
            Music m = musicService.selectMusicById(s);
            SMusic sMusic = new SMusic();
            String uuid = UUID.randomUUID().toString();
            sMusic.setId(uuid);
            sMusic.setMusicId(m.getId());
            sMusic.setShebeiId(shebeId);
            sMusic.setCreateBy(getUserId().toString());
            sMusic.setCreateTime(new Date());
            sMusic.setIsDel("0");
            musicService.insertSMusic(sMusic);
        }
        return toAjax(1);
    }

    /**
     * 歌曲下发
     */
    /*@RequiresPermissions("basis:songdown:issue")
    @PostMapping("/issue")
    @ResponseBody
    public AjaxResult issue(String shebeId, String ids) {
        List<String> ls = new ArrayList<String>();
        ls.add(shebeId);
        String[] aids = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", ls);
        map.put("musicIds", aids);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "music",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        if (!"音乐下发任务已下发".equals(msg)) {
            return toAjax(-1);
        }
        return toAjax(1);
    }*/
    /**
     * 歌曲同步
     */
//    @RequiresPermissions("basis:songdown:synchronize")
//    @PostMapping("/synchronize")
//    @ResponseBody
//    public AjaxResult synchronize(String shebeId) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("hostId", shebeId);
//        JSONObject json =new JSONObject(map);
//        log.info(json);
//        String data = HttpUtils.sendPost(MyStatic.url + "song",json);
//        log.info(data);
//        if ("".equals(data)) {
//            return toAjax(0);
//        }
//        json = JSONObject.parseObject(data);
//        String msg = json.get("msg").toString();
//        if (!"同步任务已下发".equals(msg)) {
//            return toAjax(0);
//        }
//        List<Music> lm = musicService.selectMusicList(null);
//        for (Music m: lm) {
//            SMusic s = new SMusic();
//            String uuid = UUID.randomUUID().toString();
//            s.setId(uuid);
//            s.setMusicId(m.getId());
//            s.setShebeiId(shebeId);
//            s.setCreateBy(getUserId().toString());
//            s.setCreateTime(new Date());
//            s.setIsDel("0");
//            musicService.insertSMusic(s);
//        }
//        return toAjax(1);
//    }

    /**
     * 歌曲同步
     */
    @RequiresPermissions("basis:songdown:synchronize")
    @PostMapping("/synchronizeNo")
    @ResponseBody
    public AjaxResult synchronizeNo(String shebeId) {
        List<String> ls = new ArrayList<String>();
        ls.add(shebeId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", ls);
        List<Music> musicList = musicService.selectMusicList(null);
        String[] aids = new String[musicList.size()];
        String strs = "";
        for(int i = 0;i<aids.length;i++){
            aids[i] = musicList.get(i).getId();
        }
//        map.put("musicIds", strs.substring(0,strs.length()-1));
        map.put("musicIds", aids);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "songNo",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String status = json.get("status").toString();
        Map map1 = JsonUtil.jsonToMap(json.get("data").toString());
        //缺失音乐id
        List<String> musicIds = (List<String>) map1.get(shebeId);
//        String str = json.get("data").toString();
        if (!"0".equals(status)) {
            return toAjax(0);
        }
        // 获取当前的登录用户
        User currentUser = ShiroUtils.getSysUser();
        if (musicIds == null) {
            //删除原有的
            SMusic sMusic = new SMusic();
            sMusic.setShebeiId(shebeId);
            musicService.deleteSMusicByShebeiId(sMusic);
//            List<SMusic> sMusicList = musicService.selectSMusicList(sMusic);

            for(Music m : musicList){
                sMusic.setId(StringUtils.gainGUID());
                sMusic.setMusicId(m.getId());
                sMusic.setCreateTime(new Date());
                sMusic.setCreateBy(currentUser.getUserId().toString());
                musicService.insertSMusic(sMusic);
            }
        }else{
            //删除原有的
            SMusic sMusic = new SMusic();
            sMusic.setShebeiId(shebeId);
            musicService.deleteSMusicByShebeiId(sMusic);
            //放入
            Map param = new HashMap();
            param.put("list",musicIds);
            List<Music> musicList1 = musicService.selectMusicListByMap(param);
            for(Music m : musicList1){
                sMusic.setId(StringUtils.gainGUID());
                sMusic.setMusicId(m.getId());
                sMusic.setCreateTime(new Date());
                sMusic.setCreateBy(currentUser.getUserId().toString());
                musicService.insertSMusic(sMusic);
            }
        }
        return toAjax(1);
    }


    /**
     * 删除歌曲
     */
    @RequiresPermissions("basis:songdown:issue")
    @PostMapping("/listDel")
    @ResponseBody
    public AjaxResult listDel(String shebeId, String ids) {
        List<String> ls = new ArrayList<String>();
        ls.add(shebeId);
        String[] aids = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", ls);
        map.put("musicIds", aids);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "delSong",json);
        log.info(data);

        //接口返回逻辑
        if ("".equals(data)) {
            return toAjax(0);
        }
        json = JSONObject.parseObject(data);
        String status = json.get("status").toString();
        if (!"0".equals(status)) {
            return toAjax(0);
        }
        List<Shebei> shebeiList = shebeiService.selectShebeiByIds(ls);

        //删除音乐
        List<Map<String,Object>> list = JsonUtil.toListMap(json.get("datalist").toString());
        Map<String,Object> rtnMap = new HashMap<>();
        for(Map param : list){
            rtnMap.putAll(param);
        }
        StringBuffer rtnMsg = new StringBuffer();
        for(Shebei s : shebeiList){
            String msg = "";
            if("0".equals(rtnMap.get(s.getId()).toString())){
                msg = "设备"+ s.getName() +"删除音乐成功";
                SMusic sMusic = new SMusic();
                sMusic.setShebeiId(s.getId());
                sMusic.setMusicIds(Convert.toStrArray(ids));
                musicService.deleteSMusicByIds(sMusic);
            }else{
                msg = "设备"+ s.getName() +"删除音乐失败";
            }

            rtnMsg.append(msg+"</br>");
        }
        return success(new String(rtnMsg));
    }

}