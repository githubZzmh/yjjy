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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 设备分组关系 信息操作处理
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
@Log4j2
@Controller
@RequestMapping("/basis/groupShebei")
public class ShebeiCatShebeiController extends BaseController {
    private String prefix = "basis/group" ;

    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @Autowired
    private IShebeiCatService shebeiCatService;
    @Autowired
    private IShebeiService shebeiService;

    @RequiresPermissions("basis:group:view")
    @GetMapping()
    public String shebeiCatShebei() {
        return prefix + "/shebeiCatShebei" ;
    }

    /**
     * 查询设备分组关系列表
     */
    @RequiresPermissions("basis:group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shebei shebei, String catId) {
        startPage();
        ShebeiCatShebei shebeiCatShebei = new ShebeiCatShebei();
        shebeiCatShebei.setCatId(catId);
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        if (list.size() == 0){
            return getDataTable(new ArrayList<ShebeiCatShebei>());
        }
        return getDataTable(list);
    }
    /**
     * 查询字典详细
     */
    @RequiresPermissions("basis:group:list")
    @GetMapping("/list/{catId}")
    public String detail(@PathVariable("catId") String catId, ModelMap mmap) {
        ShebeiCat shebeiCat = shebeiCatService.selectShebeiCatById(catId);
        List<ShebeiCat> listShebeiCat = shebeiCatService.selectShebeiCatList(null);
        mmap.put("shebeiCat" , shebeiCat);
        mmap.put("listShebeiCat" , listShebeiCat);
        return prefix + "/shebeiCatShebei" ;
    }
    /**
     * 导出设备分组关系列表
     */
    /*@RequiresPermissions("basis:group:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ShebeiCatShebei shebeiCatShebei) {
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        ExcelUtil<ShebeiCatShebei> util = new ExcelUtil<ShebeiCatShebei>(ShebeiCatShebei. class);
        return util.exportExcel(list, "shebeiCatShebei");
    }*/

    /**
     * 新增设备分组关系
     */
    @RequiresPermissions("basis:group:add")
    @GetMapping("/add/{catId}")
    public String add(@PathVariable("catId") String catId, ModelMap mmap) {
        ShebeiCat shebeiCat = shebeiCatService.selectShebeiCatById(catId);
        mmap.put("shebeiCat",shebeiCat);
        List<Shebei> lShebei = shebeiCatShebeiService.selectListShebeiByCatId(catId);
        if(lShebei.size() > 0){
            mmap.put("lShebei",lShebei);
            mmap.put("shebei",lShebei.get(0));
        }
        return prefix + "/catAdd" ;
    }
    /**
     * 新增保存设备分组关系
     */
    @RequiresPermissions("basis:group:add")
    @Log(title = "设备分组关系" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ShebeiCatShebei shebeiCatShebei) {
        String uuid = UUID.randomUUID().toString();
        shebeiCatShebei.setId(uuid);
        shebeiCatShebeiService.insertShebeiCatShebei(shebeiCatShebei);
        Shebei shebei = shebeiService.selectShebeiById(shebeiCatShebei.getShebeiId());
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
        map.put("catId", shebeiCatShebei.getCatId());
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
        if ("任务已下发".equals(msg)) {
            return success("修改设备成功");
        }
        return toAjax(0);
    }

    /**
     * 修改设备分组关系
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        ShebeiCatShebei shebeiCatShebei =shebeiCatShebeiService.selectShebeiCatShebeiById(id);
        mmap.put("shebeiCatShebei" , shebeiCatShebei);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存设备分组关系
     */
    @RequiresPermissions("basis:group:edit")
    @Log(title = "设备分组关系" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ShebeiCatShebei shebeiCatShebei) {
        shebeiCatShebeiService.updateShebeiCatShebei(shebeiCatShebei);
        Shebei shebei = shebeiService.selectShebeiById(shebeiCatShebei.getShebeiId());
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
        map.put("catId", shebeiCatShebei.getCatId());
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
        if ("任务已下发".equals(msg)) {
            return success("修改设备成功");
        }
        return toAjax(0);
    }

    /**
     * 删除设备分组关系
     */
    @RequiresPermissions("basis:group:remove")
    @Log(title = "设备分组关系" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(shebeiCatShebeiService.deleteShebeiCatShebeiByIds(ids));
    }
}