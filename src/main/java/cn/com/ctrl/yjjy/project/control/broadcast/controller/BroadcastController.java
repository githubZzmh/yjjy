package cn.com.ctrl.yjjy.project.control.broadcast.controller;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatService;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.PlaylistMusic;
import cn.com.ctrl.yjjy.project.basis.playlist.service.IPlaylistMusicService;
import cn.com.ctrl.yjjy.project.basis.playlist.service.IPlaylistService;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.PlaylistTimedBroadcast;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.ShebeiCatType;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.TTPlaylist;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.TimedBroadcast;
import cn.com.ctrl.yjjy.project.control.broadcast.service.BroadcastService;
import cn.com.ctrl.yjjy.project.control.broadcast.service.IPlaylistTimedBroadcastService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 定时广播操作处理
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Controller
@RequestMapping("/control/broadcast")
public class BroadcastController extends BaseController {
    private String prefix = "/control/broadcast";
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private IPlaylistService playlistService;
    @Autowired
    private IPlaylistMusicService playlistMusicService;
    @Autowired
    private IShebeiCatService shebeiCatService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @Autowired
    private IPlaylistTimedBroadcastService iPlaylistTimedBroadcastService;
    @RequiresPermissions("control:broadcast:view")
    @GetMapping()
    public String shebei(ModelMap mmap) {
        return prefix + "/view" ;
    }
    /**
     * 查询定时广播列表
     */
    @RequiresPermissions("control:broadcast:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TimedBroadcast timedBroadcast) {
        startPage();
        List<TimedBroadcast> list = broadcastService.selectListTimedBroadcast(timedBroadcast);
        return getDataTable(list);
    }
    /**
     * 查询播放列表
     */
    @RequiresPermissions("control:broadcast:list")
    @PostMapping("/leftlist")
    @ResponseBody
    public TableDataInfo leftlist(Playlist playlist) {
        startPage();
        List<Playlist> list = iPlaylistTimedBroadcastService.selectPlaylistByBroadcastId(playlist.getId());
        return getDataTable(list);
    }
    /**
     * 查询分组列表
     */
    @RequiresPermissions("control:broadcast:list")
    @PostMapping("/rightlist")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public TableDataInfo rightlist(ShebeiCat shebeiCat) {
        startPage();
        List<ShebeiCat> list = iPlaylistTimedBroadcastService.selectShebeiCatByBroadcastId(shebeiCat.getId());
        return getDataTable(list);
    }
    /**
     * 新增定时广播
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<Playlist> listPlaylist = playlistService.selectPlaylistList(null);
        mmap.put("listPlaylist" , listPlaylist);
        List<ShebeiCatType> listShebeiCatType = broadcastService.selectShebeiCatTypeList();
        mmap.put("listShebeiCatType" , listShebeiCatType);
        List<ShebeiCat> shebeiCatList_0 = listShebeiCatType.get(0).getShebeiCatList();
        List<ShebeiCat> shebeiCatList_1 = listShebeiCatType.get(1).getShebeiCatList();
        List<ShebeiCat> shebeiCatList_2 = listShebeiCatType.get(2).getShebeiCatList();
        mmap.put("shebeiCatList_0" , shebeiCatList_0);
        mmap.put("shebeiCatList_1" , shebeiCatList_1);
        mmap.put("shebeiCatList_2" , shebeiCatList_2);
        String uuid = UUID.randomUUID().toString();
        mmap.put("pkId" , uuid);
        return prefix + "/add";
    }
    /**
     * 新增播放列表
     */
    @RequiresPermissions("control:broadcast:add")
    @PostMapping("/leftlistAdd")
    @ResponseBody
    public AjaxResult leftlistSave(TTPlaylist ttplaylist) throws ParseException {
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        PlaylistTimedBroadcast chack = new PlaylistTimedBroadcast();
        chack.setBroadcastId(ttplaylist.getBroadcastId());
        chack.setPlaylistId(ttplaylist.getPlaylistId());
        chack.setBegintime(sdf.parse(ttplaylist.getBegintime()));
        chack.setEndtime(sdf.parse(ttplaylist.getEndtime()));
        List<PlaylistTimedBroadcast> lc = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyList(chack);
        if(lc.size()>0){
            return toAjax(i);
        }
        List<PlaylistTimedBroadcast> lptbc = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyByBroadcastId(ttplaylist.getBroadcastId());
        if(lptbc.size() > 0){
            for(PlaylistTimedBroadcast p:lptbc){
                String uuid = UUID.randomUUID().toString();
                PlaylistTimedBroadcast playlistTimedBroadcast = new PlaylistTimedBroadcast();
                playlistTimedBroadcast.setId(uuid);
                playlistTimedBroadcast.setBroadcastId(p.getBroadcastId());
                playlistTimedBroadcast.setShebeiCatId(p.getShebeiCatId());
                playlistTimedBroadcast.setPlaylistId(ttplaylist.getPlaylistId());
                playlistTimedBroadcast.setBegintime(sdf.parse(ttplaylist.getBegintime()));
                playlistTimedBroadcast.setEndtime(sdf.parse(ttplaylist.getEndtime()));
                if(p.getPlaylistId()==null){
                    playlistTimedBroadcast.setId(p.getId());
                    i += iPlaylistTimedBroadcastService.updatePlaylistTimedBroadcastCopyByPlaylist(playlistTimedBroadcast);
                }else{
                    i += iPlaylistTimedBroadcastService.insertPlaylistTimedBroadcastCopy(playlistTimedBroadcast);
                }
            }
            return toAjax(i);
        }else{
            String uuid = UUID.randomUUID().toString();
            PlaylistTimedBroadcast playlistTimedBroadcast = new PlaylistTimedBroadcast();
            playlistTimedBroadcast.setId(uuid);
            playlistTimedBroadcast.setBroadcastId(ttplaylist.getBroadcastId());
            playlistTimedBroadcast.setPlaylistId(ttplaylist.getPlaylistId());
            playlistTimedBroadcast.setBegintime(sdf.parse(ttplaylist.getBegintime()));
            playlistTimedBroadcast.setEndtime(sdf.parse(ttplaylist.getEndtime()));
            return toAjax(iPlaylistTimedBroadcastService.insertPlaylistTimedBroadcastCopy(playlistTimedBroadcast));
        }
    }
    /**
     * 新增分组列表
     */
    @RequiresPermissions("control:broadcast:add")
    @PostMapping("/rightlistAdd")
    @ResponseBody
    public AjaxResult rightlistSave(TTPlaylist ttplaylist) {
        int i = 0;
        PlaylistTimedBroadcast chack = new PlaylistTimedBroadcast();
        chack.setBroadcastId(ttplaylist.getBroadcastId());
        chack.setShebeiCatId(ttplaylist.getShebeiCatId());
        List<PlaylistTimedBroadcast> lc = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyList(chack);
        if(lc.size()>0){
            return toAjax(i);
        }
        List<PlaylistTimedBroadcast> lptbc = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyByBroadcastId(ttplaylist.getBroadcastId());
        if(lptbc.size() > 0){
            for(PlaylistTimedBroadcast p:lptbc){
                String uuid = UUID.randomUUID().toString();
                PlaylistTimedBroadcast playlistTimedBroadcast = new PlaylistTimedBroadcast();
                playlistTimedBroadcast.setId(uuid);
                playlistTimedBroadcast.setBroadcastId(p.getBroadcastId());
                playlistTimedBroadcast.setPlaylistId(p.getPlaylistId());
                playlistTimedBroadcast.setShebeiCatId(ttplaylist.getShebeiCatId());
                playlistTimedBroadcast.setBegintime(p.getBegintime());
                playlistTimedBroadcast.setEndtime(p.getEndtime());
                if(p.getShebeiCatId()==null){
                    playlistTimedBroadcast.setId(p.getId());
                    i += iPlaylistTimedBroadcastService.updatePlaylistTimedBroadcastCopyByShebeiCat(playlistTimedBroadcast);
                }else{
                    i += iPlaylistTimedBroadcastService.insertPlaylistTimedBroadcastCopy(playlistTimedBroadcast);
                }
            }
            return toAjax(i);
        }else{
            String uuid = UUID.randomUUID().toString();
            PlaylistTimedBroadcast playlistTimedBroadcast = new PlaylistTimedBroadcast();
            playlistTimedBroadcast.setId(uuid);
            playlistTimedBroadcast.setBroadcastId(ttplaylist.getBroadcastId());
            playlistTimedBroadcast.setShebeiCatId(ttplaylist.getShebeiCatId());
            return toAjax(iPlaylistTimedBroadcastService.insertPlaylistTimedBroadcastCopy(playlistTimedBroadcast));
        }
    }
    /**
     * 删除播放列表
     */
    @RequiresPermissions("control:broadcast:add")
    @PostMapping("/leftlistRemove")
    @ResponseBody
    public AjaxResult leftlistRemove(TTPlaylist ttplaylist) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        PlaylistTimedBroadcast p = new PlaylistTimedBroadcast();
        p.setBroadcastId(ttplaylist.getBroadcastId());
        p.setPlaylistId(ttplaylist.getPlaylistId());
        p.setBegintime(sdf.parse(ttplaylist.getBegintime()));
        p.setEndtime(sdf.parse(ttplaylist.getEndtime()));
        try {
            int i = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyCountByPlaylist(p);
            if(i > 0){
                return toAjax(iPlaylistTimedBroadcastService.deletePlaylistTimedBroadcastCopy(p));
            }else{
                p.setPlaylistId(null);
                p.setBegintime(null);
                p.setEndtime(null);
                return toAjax(iPlaylistTimedBroadcastService.updatePlaylistTimedBroadcastByPlaylist(p));
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    /**
     * 删除分组列表
     */
    @RequiresPermissions("control:broadcast:add")
    @PostMapping("/rightlistRemove")
    @ResponseBody
    public AjaxResult rightlistRemove(TTPlaylist ttplaylist) {
        PlaylistTimedBroadcast p = new PlaylistTimedBroadcast();
        p.setBroadcastId(ttplaylist.getBroadcastId());
        p.setPlaylistId(ttplaylist.getPlaylistId());
        p.setShebeiCatId(ttplaylist.getShebeiCatId());
        try {
            int i = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyCountByShebeiCat(p);
            if(i > 0){
                return toAjax(iPlaylistTimedBroadcastService.deletePlaylistTimedBroadcastCopy(p));
            }else{
                p.setShebeiCatId(null);
                return toAjax(iPlaylistTimedBroadcastService.updatePlaylistTimedBroadcastByShebeiCat(p));
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    /**
     * 新增保存定时广播
     */
    @RequiresPermissions("control:broadcast:add")
    @Log(title = "定时广播" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TimedBroadcast timedBroadcast) {
        String broadcastId = timedBroadcast.getId();
        iPlaylistTimedBroadcastService.deletePlaylistTimedBroadcastCopyNull();
        List<PlaylistTimedBroadcast> lptbc = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyByBroadcastId(broadcastId);
        for(PlaylistTimedBroadcast p:lptbc){
            iPlaylistTimedBroadcastService.insertPlaylistTimedBroadcast(p);
            //获取设备ids
            Set<String> shebeiIds = new TreeSet<String>();
            //获取音乐ids
            Set<String> musicIds = new TreeSet<String>();
            PlaylistMusic playlistMusic = new PlaylistMusic();
            playlistMusic.setPlaylistId(p.getPlaylistId());
            List<PlaylistMusic> lpm = playlistMusicService.selectListPlaylistMusicByPlaylistMusic(playlistMusic);
            for (PlaylistMusic pm: lpm) {
                musicIds.add(pm.getMusicId());
            }
            ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
            shebeiCatShebei.setCatId(p.getShebeiCatId());
            List<ShebeiCatShebei>  lscs = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
            for (ShebeiCatShebei scs: lscs) {
                shebeiIds.add(scs.getShebeiId());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String b = sdf.format(p.getBegintime());
            String e = sdf.format(p.getEndtime());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("hostIds", shebeiIds);
            map.put("musicIds", musicIds);
            map.put("time", b.substring(0, b.length() - 3) + e.substring(0, e.length() - 3));
            JSONObject json = new JSONObject(map);
            log.info(json);
            String data = HttpUtils.sendPost(MyStatic.url + "timBroadCast",json);
            log.info(data);
        }
        return toAjax(broadcastService.insertTimedBroadcast(timedBroadcast));
    }
    /**
     * 修改定时广播
     */
    @GetMapping("/edit/{broadcastId}")
    public String edit(@PathVariable("broadcastId") String broadcastId, ModelMap mmap) {
        List<Playlist> listPlaylist = playlistService.selectPlaylistList(null);
        mmap.put("listPlaylist" , listPlaylist);
        List<ShebeiCatType> listShebeiCatType = broadcastService.selectShebeiCatTypeList();
        mmap.put("listShebeiCatType" , listShebeiCatType);
        List<ShebeiCat> shebeiCatList_0 = listShebeiCatType.get(0).getShebeiCatList();
        List<ShebeiCat> shebeiCatList_1 = listShebeiCatType.get(1).getShebeiCatList();
        List<ShebeiCat> shebeiCatList_2 = listShebeiCatType.get(2).getShebeiCatList();
        mmap.put("shebeiCatList_0" , shebeiCatList_0);
        mmap.put("shebeiCatList_1" , shebeiCatList_1);
        mmap.put("shebeiCatList_2" , shebeiCatList_2);
        TimedBroadcast timedBroadcast = broadcastService.selectTimedBroadcastById(broadcastId);
        mmap.put("pkName" , timedBroadcast.getName());
        mmap.put("pkId" , timedBroadcast.getId());
        //根据规则id查询定时广播关系表
        List<PlaylistTimedBroadcast> lptb = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastByBroadcastId(broadcastId);
        iPlaylistTimedBroadcastService.deletePlaylistTimedBroadcastCopyByBroadcastId(broadcastId);
        for(PlaylistTimedBroadcast p:lptb){
            iPlaylistTimedBroadcastService.insertPlaylistTimedBroadcastCopy(p);
        }
        return prefix + "/edit" ;
    }
    /**
     * 修改保存定时广播
     */
    @RequiresPermissions("control:broadcast:edit")
    @Log(title = "定时广播" , businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TimedBroadcast timedBroadcast) {
        String broadcastId = timedBroadcast.getId();
        iPlaylistTimedBroadcastService.deletePlaylistTimedBroadcastCopyNull();
        List<PlaylistTimedBroadcast> lptbc = iPlaylistTimedBroadcastService.selectPlaylistTimedBroadcastCopyByBroadcastId(broadcastId);
        iPlaylistTimedBroadcastService.deletePlaylistTimedBroadcastByBroadcastId(broadcastId);
        for(PlaylistTimedBroadcast p:lptbc){
            iPlaylistTimedBroadcastService.insertPlaylistTimedBroadcast(p);
            //获取设备ids
            Set<String> shebeiIds = new TreeSet<String>();
            //获取音乐ids
            Set<String> musicIds = new TreeSet<String>();
            PlaylistMusic playlistMusic = new PlaylistMusic();
            playlistMusic.setPlaylistId(p.getPlaylistId());
            List<PlaylistMusic> lpm = playlistMusicService.selectListPlaylistMusicByPlaylistMusic(playlistMusic);
            for (PlaylistMusic pm: lpm) {
                musicIds.add(pm.getMusicId());
            }
            ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
            shebeiCatShebei.setCatId(p.getShebeiCatId());
            List<ShebeiCatShebei>  lscs = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
            for (ShebeiCatShebei scs: lscs) {
                shebeiIds.add(scs.getShebeiId());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String b = sdf.format(p.getBegintime());
            String e = sdf.format(p.getEndtime());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("hostIds", shebeiIds);
            map.put("musicIds", musicIds);
            map.put("time", b.substring(0, b.length() - 3) + e.substring(0, e.length() - 3));
            JSONObject json = new JSONObject(map);
            log.info(json);
            String data = HttpUtils.sendPost(MyStatic.url + "timBroadCast",json);
            log.info(data);
        }
        return toAjax(broadcastService.updateTimedBroadcast(timedBroadcast));
    }
    /**
     * 删除定时广播
     */
    @RequiresPermissions("control:broadcast:remove")
    @Log(title = "定时广播" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        iPlaylistTimedBroadcastService.deletePlaylistTimedBroadcastByIds(ids);
        return toAjax(broadcastService.deleteTimedBroadcastByIds(ids));
    }
}