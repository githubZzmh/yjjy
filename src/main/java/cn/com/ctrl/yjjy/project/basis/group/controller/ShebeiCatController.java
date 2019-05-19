package cn.com.ctrl.yjjy.project.basis.group.controller;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatService;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/**
 * 设备分组 信息操作处理
 *
 * @author zzmh
 * @date 2018-11-30
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/group")
public class ShebeiCatController extends BaseController {
    private String prefix = "/basis/group" ;
    @Autowired
    private IShebeiCatService shebeiCatService;
    @Autowired
    private IShebeiService shebeiService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @RequiresPermissions("basis:group:view")
    @GetMapping()
    public String shebeiCat() {
        return prefix + "/shebeiCat" ;
    }
    /**
     * 查询设备分组列表
     */
    @RequiresPermissions("basis:group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShebeiCat shebeiCat) {
        startPage();
        List<ShebeiCat> list = shebeiCatService.selectShebeiCatList(shebeiCat);
        return getDataTable(list);
    }
    /**
     * 导出设备分组列表
     */
    /*@RequiresPermissions("basis:group:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ShebeiCat shebeiCat) {
        List<ShebeiCat> list = shebeiCatService.selectShebeiCatList(shebeiCat);
        ExcelUtil<ShebeiCat> util = new ExcelUtil<ShebeiCat>(ShebeiCat. class);
        return util.exportExcel(list, "shebeiCat");
    }*/
    /**
     * 新增设备分组
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }
    /**
     * 新增保存设备分组
     */
    @RequiresPermissions("basis:group:add")
    @Log(title = "设备分组" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ShebeiCat shebeiCat) {
        String uuid = UUID.randomUUID().toString();
        shebeiCat.setId(uuid);
        /*if("9".equals(shebeiCat.getType())){
            shebeiCat.setType("");
        }*/
        return toAjax(shebeiCatService.insertShebeiCat(shebeiCat));
    }
    /**
     * 修改设备分组
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        ShebeiCat shebeiCat =shebeiCatService.selectShebeiCatById(id);
        mmap.put("shebeiCat" , shebeiCat);
        return prefix + "/edit" ;
    }
    /**
     * 修改保存设备分组
     */
    @RequiresPermissions("basis:group:edit")
    @Log(title = "设备分组" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ShebeiCat shebeiCat) {
        shebeiCatService.updateShebeiCat(shebeiCat);
        ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
        shebeiCatShebei.setCatId(shebeiCat.getId());
        List<ShebeiCatShebei> lshebeiCatShebei = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        for (ShebeiCatShebei shebeiCatShebeis: lshebeiCatShebei){
            Shebei shebei = shebeiService.selectShebeiById(shebeiCatShebeis.getShebeiId());
            HashMap map = new HashMap<String, Object>();
            //设备id
            map.put("hostId", shebei.getId());
            //主机号
            map.put("hostNum", shebei.getNumId());
            //ip地址
            map.put("hostIp", shebei.getIp());
            //网关
            map.put("gateway", shebei.getGateway());
            //子网掩码
            map.put("subnet_mask", shebei.getMask());
            //分组地址Id
            map.put("catId", shebeiCat.getId());
            //sipIP地址
            map.put("sipIp", shebei.getSipIp());
            //端口号
            map.put("sipPort", shebei.getSipPort());
            //SIP密码
            map.put("sipPwd", shebei.getSipPwd());
            //sip号
            map.put("sipId", shebei.getSipId());
            JSONObject json =new JSONObject(map);
            log.info(json);
            String data = HttpUtils.sendPost(MyStatic.url + "host",json);
            log.info(data);
            json = JSONObject.parseObject(data);
            String msg = json.get("msg").toString();
            if (!"任务已下发".equals(msg)) {
                return toAjax(0);
            }
        }
        return success("修改设备成功");
    }
    /**
     * 删除设备分组
     */
    @RequiresPermissions("basis:group:remove")
    @Log(title = "设备分组" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(shebeiCatService.deleteShebeiCatByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}