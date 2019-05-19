package cn.com.ctrl.yjjy.project.basis.audio.controller;
import java.io.*;
import java.util.Date;
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
import org.springframework.stereotype.Controller;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.project.basis.audio.domain.Music;
import cn.com.ctrl.yjjy.project.basis.audio.service.IMusicService;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;
/**
 * 音乐 信息操作处理
 *
 * @author zzmh
 * @date 2018-12-03
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/audio")
public class MusicController extends BaseController {
    private String prefix = "basis/audio" ;

    @Autowired
    private IMusicService musicService;

    @RequiresPermissions("basis:audio:view")
    @GetMapping()
    public String music() {
        return prefix + "/music" ;
    }

    /**
     * 查询音乐列表
     */
    @RequiresPermissions("basis:audio:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Music music) {
        startPage();
        List<Music> list = musicService.selectMusicList(music);
        return getDataTable(list);
    }


    /**
     * 导出音乐列表
     */
    @RequiresPermissions("basis:audio:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Music music) {
        List<Music> list = musicService.selectMusicList(music);
        ExcelUtil<Music> util = new ExcelUtil<Music>(Music. class);
        return util.exportExcel(list, "music");
    }

    /**
     * 新增音乐
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存音乐
     */
    @RequiresPermissions("basis:audio:add")
    @Log(title = "音乐" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Music music) throws IOException {
        String outUrl = "C:\\fakepath\\out";
        File file=new File(music.getFileSrc()+"\\"+music.getFilename());
        if(!file.exists()){
            file.createNewFile();
        }
        FileInputStream fis=new FileInputStream(file);
        FileOutputStream fos=new FileOutputStream(outUrl+"\\"+music.getFilename());
        BufferedInputStream bis=new BufferedInputStream(fis);
        BufferedOutputStream bos=new BufferedOutputStream(fos);
        int b;
        while((b=fis.read())!=-1){
            bos.write(b);
        }
        bis.close();
        bos.close();
        String uuid = UUID.randomUUID().toString();
        music.setId(uuid);
        music.setCreatetime(new Date());
        music.setFileSrc(outUrl+"\\"+music.getFilename());
        return toAjax(musicService.insertMusic(music));
    }

    /**
     * 修改音乐
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Music music =musicService.selectMusicById(id);
        mmap.put("music" , music);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存音乐
     */
    @RequiresPermissions("basis:audio:edit")
    @Log(title = "音乐" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Music music) {
        return toAjax(musicService.updateMusic(music));
    }

    /**
     * 删除音乐
     */
    @RequiresPermissions("basis:audio:remove")
    @Log(title = "音乐" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(musicService.deleteMusicByIds(ids));
    }
}