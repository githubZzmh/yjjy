package cn.com.ctrl.yjjy.project.control.conelrad.controller;
import cn.com.ctrl.yjjy.common.utils.StringUtils;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.control.conelrad.domain.ConelradView;
import cn.com.ctrl.yjjy.project.control.conelrad.service.ConelradService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import cn.com.ctrl.yjjy.project.tool.UtilsJson;
import com.alibaba.fastjson.JSONArray;
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
 * 紧急广播操作处理
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Controller
@RequestMapping("/control/conelrad")
public class ConelradController extends BaseController {
    private String prefix = "/control/conelrad";
    @Autowired
    private ConelradService conelradService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @RequiresPermissions("control:conelrad:view")
    @GetMapping()
    public String shebei(ModelMap mmap) {
        return prefix + "/view" ;
    }
    /**
     * 查询设备列表
     */
    @RequiresPermissions("control:conelrad:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShebeiCatShebei shebeiCatShebei) {
        startPage();
        List<ShebeiCatShebei> list = shebeiCatShebeiService.selectShebeiCatShebeiList(shebeiCatShebei);
        List<ConelradView> listc = new ArrayList<ConelradView>();
        for (ShebeiCatShebei s: list) {
            ConelradView conelradView = new ConelradView();
            conelradView.setId(s.getId());
            conelradView.setNumId(s.getShebei().getNumId());
            conelradView.setName(s.getShebeiCat().getName());
            conelradView.setIp(s.getShebei().getIp());
            conelradView.setPointX(String.valueOf(s.getShebei().getPointX()));
            conelradView.setPointY(String.valueOf(s.getShebei().getPointY()));
            listc.add(conelradView);
        }
        return getDataTable(listc);
    }
    /**
     * 播放
     */
    @RequiresPermissions("control:conelrad:play")
    @PostMapping("/play")
    @ResponseBody
    public AjaxResult play(String ids) {
        String[] aids = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        //机器ids
        map.put("hostIds", aids);
        //广播标识 1开始 0结束
        map.put("broadStatus", "1");
        //1在线 0紧急
        map.put("broadOnline", "0");
        //String json = UtilsJson.toJson(map);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "broad",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        if (!"任务已下发".equals(msg)) {
            return toAjax(-1);
        }
        return toAjax(1);
    }
    /**
     * 停止
     */
    @RequiresPermissions("control:conelrad:stop")
    @PostMapping("/stop")
    @ResponseBody
    public AjaxResult stop(String ids) {
        String[] aids = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        //设备ids
        map.put("hostIds", aids);
        //广播标识 1开始 0结束
        map.put("broadStatus", "0");
        //1在线 0紧急
        map.put("broadOnline", "0");
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "broad",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        if (!"任务已下发".equals(msg)) {
            return toAjax(-1);
        }
        return toAjax(1);
    }
}