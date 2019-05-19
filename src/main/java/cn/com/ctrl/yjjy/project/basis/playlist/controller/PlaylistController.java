package cn.com.ctrl.yjjy.project.basis.playlist.controller;

import java.util.List;
import java.util.UUID;

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
import org.springframework.stereotype.Controller;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import cn.com.ctrl.yjjy.project.basis.playlist.service.IPlaylistService;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;

/**
 * 播放列 信息操作处理
 *
 * @author yjjy
 * @date 2018-12-04
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/playlist")
public class PlaylistController extends BaseController {
    private String prefix = "basis/playlist" ;

    @Autowired
    private IPlaylistService playlistService;

    @RequiresPermissions("basis:playlist:view")
    @GetMapping()
    public String playlist() {
        return prefix + "/playlist" ;
    }

    /**
     * 查询播放列列表
     */
    @RequiresPermissions("basis:playlist:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Playlist playlist) {
        startPage();
        List<Playlist> list = playlistService.selectPlaylistList(playlist);
        return getDataTable(list);
    }

    /**
     * 导出播放列列表
     */
    @RequiresPermissions("basis:playlist:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Playlist playlist) {
        List<Playlist> list = playlistService.selectPlaylistList(playlist);
        ExcelUtil<Playlist> util = new ExcelUtil<Playlist>(Playlist. class);
        return util.exportExcel(list, "playlist");
    }

    /**
     * 新增播放列
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存播放列
     */
    @RequiresPermissions("basis:playlist:add")
    @Log(title = "播放列" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Playlist playlist) {
        String uuid = UUID.randomUUID().toString();
        playlist.setId(uuid);
        return toAjax(playlistService.insertPlaylist(playlist));
    }

    /**
     * 修改播放列
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Playlist playlist =playlistService.selectPlaylistById(id);
        mmap.put("playlist" , playlist);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存播放列
     */
    @RequiresPermissions("basis:playlist:edit")
    @Log(title = "播放列" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Playlist playlist) {
        return toAjax(playlistService.updatePlaylist(playlist));
    }

    /**
     * 删除播放列
     */
    @RequiresPermissions("basis:playlist:remove")
    @Log(title = "播放列" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(playlistService.deletePlaylistByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}