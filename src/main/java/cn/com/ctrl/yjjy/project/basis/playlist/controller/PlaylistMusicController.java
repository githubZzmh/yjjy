package cn.com.ctrl.yjjy.project.basis.playlist.controller;
import java.util.List;
import java.util.UUID;

import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import cn.com.ctrl.yjjy.project.basis.playlist.service.IPlaylistService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.PlaylistMusic;
import cn.com.ctrl.yjjy.project.basis.playlist.service.IPlaylistMusicService;
import org.springframework.stereotype.Controller;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;
/**
 * 播放列音乐关系 信息操作处理
 *
 * @author zzmh
 * @date 2018-12-04
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/playlistMusic")
public class PlaylistMusicController extends BaseController {
    private String prefix = "basis/playlist" ;
    @Autowired
    private IPlaylistMusicService playlistMusicService;
    @Autowired
    private IPlaylistService playlistService;
    /**
     * 查询字典详细
     */
    @RequiresPermissions("basis:playlist:list")
    @GetMapping("/list/{playlistId}")
    public String detail(@PathVariable("playlistId") String playlistId, ModelMap mmap) {
        Playlist playlist = playlistService.selectPlaylistById(playlistId);
        List<Playlist> listPlaylist = playlistService.selectPlaylistList(null);
        mmap.put("playlist" , playlist);
        mmap.put("listPlaylist" , listPlaylist);
        return prefix + "/playlistMusic" ;
    }
    /**
     * 查询播放列音乐关系列表
     */
    @RequiresPermissions("basis:playlist:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PlaylistMusic playlistMusic) {
        startPage();
        List<PlaylistMusic> list = playlistMusicService.selectListPlaylistMusicByPlaylistMusic(playlistMusic);
        return getDataTable(list);
    }
    /**
     * 导出播放列音乐关系列表
     */
    @RequiresPermissions("basis:playlist:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PlaylistMusic playlistMusic) {
        List<PlaylistMusic> list = playlistMusicService.selectPlaylistMusicList(playlistMusic);
        ExcelUtil<PlaylistMusic> util = new ExcelUtil<PlaylistMusic>(PlaylistMusic. class);
        return util.exportExcel(list, "playlistMusic");
    }

    /**
     * 新增播放列音乐关系
     */
    @RequiresPermissions("basis:playlist:add")
    @GetMapping("/add/{playlistId}")
    public String add(@PathVariable("playlistId") String playlistId, ModelMap mmap) {
        Playlist playlist = playlistService.selectPlaylistById(playlistId);
        mmap.put("playlist",playlist);
        List<Music> lMusic = playlistMusicService.selectPlaylistMusicByPlaylistId(playlistId);
        if(lMusic.size() > 0){
            mmap.put("lMusic",lMusic);
            mmap.put("music",lMusic.get(0));
        }
        return prefix + "/musicAdd" ;
    }

    /**
     * 新增保存播放列音乐关系
     */
    @RequiresPermissions("basis:playlist:add")
    @Log(title = "播放列音乐关系" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PlaylistMusic playlistMusic) {
        String uuid = UUID.randomUUID().toString();
        playlistMusic.setId(uuid);
        return toAjax(playlistMusicService.insertPlaylistMusic(playlistMusic));
    }

    /**
     * 修改播放列音乐关系
     */
    @GetMapping("/edit/{playlistId}")
    public String edit(@PathVariable("playlistId") String playlistId, ModelMap mmap) {
        Playlist playlist = playlistService.selectPlaylistById(playlistId);
        mmap.put("playlist",playlist);
        List<Music> lMusic = playlistMusicService.selectPlaylistMusicByPlaylistId(playlistId);
        if(lMusic.size() > 0){
            mmap.put("lMusic",lMusic);
            mmap.put("music",lMusic.get(0));
        }
        return prefix + "/musicEdit" ;
    }

    /**
     * 修改保存播放列音乐关系
     */
    @RequiresPermissions("basis:playlist:edit")
    @Log(title = "播放列音乐关系" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PlaylistMusic playlistMusic) {
        return toAjax(playlistMusicService.updatePlaylistMusic(playlistMusic));
    }

    /**
     * 删除播放列音乐关系
     */
    @RequiresPermissions("basis:playlist:remove")
    @Log(title = "播放列音乐关系" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(playlistMusicService.deletePlaylistMusicByIds(ids));
    }
}