package cn.com.ctrl.yjjy.project.system.user.controller;
import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.control.plan.domain.ShebeiPoints;
import cn.com.ctrl.yjjy.project.control.plan.service.IPlanRoleService;
import cn.com.ctrl.yjjy.project.control.plan.service.IPointService;
import cn.com.ctrl.yjjy.project.control.plan.service.IShebeiPointsService;
import cn.com.ctrl.yjjy.project.rescue.aisle.domain.Aisle;
import cn.com.ctrl.yjjy.project.rescue.aisle.service.IAisleService;
import cn.com.ctrl.yjjy.project.system.user.entity.PlanView;
import cn.com.ctrl.yjjy.project.system.user.entity.Polyline;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import cn.com.ctrl.yjjy.project.tool.map.controller.DijsktraController;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.project.tool.map.service.IDijsktraService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 预案
 *
 * @author zzmh
 */
@Log4j2
@Controller
@RequestMapping("/plan")
public class PlanController extends BaseController {
    @Autowired
    private IPointService pointService;
    @Autowired
    private IShebeiService shebeiService;
    @Autowired
    private IAisleService aisleService;
    @Autowired
    private IDijsktraService dijsktraService;
    @Autowired
    private IShebeiPointsService shebeiPointsService;
    @Autowired
    private IPlanService planService;
    /**
     * 新增预案
     */
    @PostMapping("/addd")
    @ResponseBody
    public AjaxResult addSave_(PlanView planView) {
        //获得开始位置
        List<Dijsktra> ldop = dijsktraService.selectDijsktraOp(planView.getSid());
        //获得结束位置列表
        List<Dijsktra> lded = dijsktraService.selectDijsktraEd(planView.getPlanId());
        //有效开始疏散点在矩阵中的位置
        int begin = -1;
        //有效疏散点在矩阵中的位置集合
        List<Integer> liEnd = new ArrayList<Integer>();
        //有效全域在列表中的位置
        int list = -1;
        //有效全域组
        String[] larr = {};
        //查找全域列表
        List<Dijsktra> ld = dijsktraService.selectDijsktraGroupByAlarr(null);
        //循环全域列表
        for (int i = 0;i < ld.size();i++) {
            Dijsktra d = ld.get(i);
            //当前全域列表与开始位置全域一致
            if (d.getAlarr().equals(ldop.get(0).getAlarr())) {
                //标记当前全域在列表中的位置
                list = i;
                //取得有效全域组
                larr = d.getAlarr().split(",");
                List<Integer> li = new ArrayList<Integer>();
                for (String s: larr) {
                    li.add(Integer.valueOf(s));
                }
                for (int ii = 0;ii < lded.size();ii++) {
                    int numop = Integer.valueOf(ldop.get(0).getWeightNum());
                    int num = Integer.valueOf(lded.get(ii).getWeightNum());
                    for (int iii = 0;iii < li.size();iii++) {
                        int ili = li.get(iii);
                        if (ili == numop) {
                            begin = iii;
                        }
                        if (ili == num) {
                            liEnd.add(iii);
                            break;
                        }
                    }
                }
            } else {
                if (liEnd.size()==0) {
                    list = -1;
                }
            }
        }
        //判断当前预案是否可执行
        if (list != -1 && liEnd.size() > 0 && begin != -1) {
            //演算路程矩阵
            double[][] arr = Dijsktra(ld.get(list).getAlarr());
            //获得途径路径
            List<String> ls = new ArrayList<String>();
            for (int i = 0;i < liEnd.size();i++) {
                ls.add(DijsktraController.dijsktra(arr, String.valueOf(begin), liEnd.get(i).toString()));
            }
            List<Polyline> lpolyline = new ArrayList<Polyline>();
            //循环读取途径路径
            for (String s: ls) {
                List<String> larrs = new ArrayList<String>();
                String[] sarr = s.split("->");
                for (String ss: sarr) {
                    int ssi = Integer.valueOf(ss);
                    //定位途径点
                    larrs.add(larr[ssi]);
                }
                //根据标点矩阵编号划线
                lpolyline.addAll(Scribing(larrs));
            }
            Set<Polyline> ts = new HashSet<Polyline>();
            ts.addAll(lpolyline);
            //传递划线
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.put("bool","true");
            Object plPoints = JSONArray.toJSON(ts);
            ajaxResult.put("myplPoints",plPoints);
            return ajaxResult;
        } else {
            //传递前台说明不可执行
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.put("bool","false");
            return ajaxResult;
        }
    }
    /**
     * 新增预案
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PlanView planView) {
        //获得开始位置
        List<Dijsktra> ldop = dijsktraService.selectDijsktraOp(planView.getSid());
        Dijsktra ksbd = ldop.get(0);
        //获得结束位置列表
        List<Dijsktra> lded = dijsktraService.selectDijsktraEd(planView.getPlanId());
        Dijsktra jsbd = lded.get(0);
        //查找全域列表
        List<Dijsktra> ld = dijsktraService.selectDijsktraGroupByAlarr(null);
        //有效全域在列表中的位置
        int list = -1;
        //循环全域列表
        for (int i = 0;i < ld.size();i++) {
            Dijsktra d = ld.get(i);
            //当前全域列表与开始位置全域一致
            if (d.getAlarr().equals(ksbd.getAlarr()) && jsbd.getAlarr().equals(ksbd.getAlarr())) {
                //标记当前全域在列表中的位置
                list = i;
            }
        }
        if (list < 0) {
            //传递前台说明不可执行
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.put("bool","false");
            return ajaxResult;
        }
        //获得有效全域列表
        String arr = ld.get(list).getAlarr();
        //取得有效全域组
        List<String> larr = Arrays.asList(arr.split(","));
        //演算路程矩阵
        double[][] darr = Dijsktra(arr);
        int op = 0, ed = 0;
        for (int i = 0;i < larr.size();i++) {
            if (ksbd.getWeightNum().equals(larr.get(i))) {
                op = i;
            }
            if (jsbd.getWeightNum().equals(larr.get(i))) {
                ed = i;
            }
        }
        String path = DijsktraController.dijsktra2(darr, op, ed);
        List<String> larrs = new ArrayList<String>();
        String[] sarr = path.split("->");
        for (String ss: sarr) {
            int ssi = Integer.valueOf(ss);
            //定位途径点
            larrs.add(larr.get(ssi));
        }
        List<Polyline> lpolyline = new ArrayList<Polyline>();
        //根据标点矩阵编号划线
        lpolyline.addAll(Scribing(larrs));
        Set<Polyline> ts = new HashSet<Polyline>();
        ts.addAll(lpolyline);
        //传递划线
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("bool","true");
        Object plPoints = JSONArray.toJSON(ts);
        ajaxResult.put("myplPoints",plPoints);
        //根据有效全域arr查询有效的标点路程
        //查询有效的标点列表
        List<Dijsktra> lDijsktra = dijsktraService.selectDijsktraListByNums(arr);
        //循环查询对应路程第一段
        ArrayList<String> larrss = new ArrayList<String>();
        ArrayList<String> larryx = new ArrayList<String>();
        for (int i = 0;i < lDijsktra.size();i++) {
            String f = DijsktraController.dijsktra2(darr, i, ed);
            larrs = new ArrayList<String>();
            String[] ff = f.split("->");
            for (String ss: ff) {
                int ssi = Integer.valueOf(ss);
                //定位途径点
                larrs.add(larr.get(ssi));
            }
            if (!larrs.get(0).equals(larrs.get(1))) {
                //查询第一标点是否为设备
                String pid1 = dijsktraService.selectDijsktraListByNums(larrs.get(0)).get(0).getPointId();
                String pid2 = dijsktraService.selectDijsktraListByNums(larrs.get(1)).get(0).getPointId();
                Shebei s1 = shebeiService.selectShebeiById(pid1);
                Shebei s2 = shebeiService.selectShebeiById(pid2);
                if (s1 != null && !"".equals(s1.getId())) {
                    int ii = 1;
                    for (;(s2 == null || "".equals(s2.getId())) && ii < ff.length;ii++){
                        pid2 = dijsktraService.selectDijsktraListByNums(larrs.get(ii)).get(0).getPointId();
                        s2 = shebeiService.selectShebeiById(pid2);
                    }
                    if (s2 != null && !"".equals(s2.getId())) {
                        //下发图文播报逃生路线
                        larrss.add(s1.getName() + "至" + s2.getName());
                        larryx.add(larrs.get(0) + "至" + larrs.get(ii));
                    }
                }
            }
        }
        if (larrss.size() > 0) {
            //给所有设备发送解除险情
            //List<Shebei> jc = shebeiService.selectShebeiList(null);
            //List<String> jcsbids = new ArrayList<String>();
            //for (Shebei sids: jc) {
                //jcsbids.add(sids.getId());
            //}
            //Object idsJSON = JSONArray.toJSON(jcsbids);
            //Map<String, Object> map = new HashMap<String, Object>();
            //map.put("hostIds", idsJSON);
            //JSONObject json = new JSONObject(map);
            //log.info(json);
            //String data = HttpUtils.sendPost("http://192.168.4.101:8080/yjjy/tcp/rmovedanger", json);
            //log.info(data);
            //json = JSONObject.parseObject(data);
            //String msg = json.get("msg").toString();
            //if (!"任务已下发".equals(msg)) {
                //ajaxResult.put("bool","false");
            //}
            //循环查询
            //下发图文播报逃生路线
            for (int num = 0;num < larrss.size();num++) {
                String lx = larryx.get(num);
                ShebeiPoints sp = shebeiPointsService.selectShebeiPointsById(lx);
                String[] bd = lx.split("至");
                Shebei sbe = new Shebei();
                sbe.setId(dijsktraService.selectDijsktraListByNums(bd[1]).get(0).getPointId());
                List<Shebei> jc = shebeiService.selectShebeiList(sbe);
                if (jc != null && jc.size() > 0) {
                    //生成语音
                    String bpoint, epoint, year, month, day, time, minute, second, accident, route, name;
                    bpoint = ldop.get(0).getPointName();
                    epoint = jc.get(0).getName();
                    bd = planService.selectPlanById(planView.getPlanId()).getName().split("事故");
                    accident = bd[0];
                    route =sp.getRoute();
                    name = sp.getBroadName();
                    Calendar now = Calendar.getInstance();
                    year = now.get(Calendar.YEAR) + "";
                    month = (now.get(Calendar.MONTH) + 1) + "";
                    day = now.get(Calendar.DAY_OF_MONTH) + "";
                    time = now.get(Calendar.HOUR_OF_DAY) + "";
                    minute = now.get(Calendar.MINUTE) + "";
                    second = now.get(Calendar.SECOND) + "";
                    DijsktraController.mp3(bpoint, epoint, year, month, day, time, minute, second, accident, route, name);
                    //发送
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("hostId", jc.get(0).getId());
                    map.put("mapName", sp.getMapName());
                    map.put("mapUrl", sp.getMapUrl() + sp.getMapName());
                    map.put("broadName", sp.getBroadName());
                    map.put("broadUrl", sp.getBroadUrl() + sp.getBroadName());
                    JSONObject json =new JSONObject(map);
                    log.info(json);
                    String data = HttpUtils.sendPost(MyStatic.url + "escape",json);//("http://192.168.4.101:8080/yjjy/tcp/escape", json);
                    log.info(data);
                    json = JSONObject.parseObject(data);
                    String msg = json.get("msg").toString();
                    if (!"任务已下发".equals(msg)) {
                        ajaxResult.put("bool","false");
                    }
                }
            }

        }
        return ajaxResult;
    }
    /**
     * 演算路程矩阵
     * @param alarr 全域字符串
     * @return 路程矩阵
     */
    private double[][] Dijsktra (String alarr) {
        //查询全域标点列表
        Dijsktra d = new Dijsktra();
        d.setAlarr(alarr);
        List<Dijsktra> ld = dijsktraService.selectDijsktraList(d);
        double[][] dou = new double[ld.size()][ld.size()];
        for (int i = 0;i < ld.size();i++) {
            for (int ii = 0;ii < ld.size();ii++) {
                Point a = pointService.selectPointById(ld.get(i).getPointId());
                Point b = pointService.selectPointById(ld.get(ii).getPointId());
                if (i == ii) {
                    dou[i][ii] = 0;
                } else {
                    dou[i][ii] = Distance(a, b);
                }
            }
        }
        return dou;
    }
    /**
     * 查找两标点距离
     * @param a 标点1
     * @param b 标点2
     * @return 两标点距离
     */
    private double Distance (Point a, Point b) {
        Aisle aisle = new Aisle();
        aisle.setBeginPointX(a.getPointX());
        aisle.setBeginPointY(a.getPointY());
        aisle.setEndPointX(b.getPointX());
        aisle.setEndPointY(b.getPointY());
        aisle.setStatus("0");
        List<Aisle> la = aisleService.selectAisleList_(aisle);
        if (la.size() > 0) {
            return la.get(0).getDistance();
        } else {
            return 1000;
        }
    }
    /**
     * 根据标点矩阵编号划线
     * @param larrs 途径定位点num列表
     * @return 折线
     */
    private List<Polyline> Scribing (List<String> larrs) {
        //折线列表
        List<Polyline> lpolyline = new ArrayList<Polyline>();
        String sa = larrs.get(0);
        String sb = larrs.get(1);
        if (sa.equals(sb)){
            return lpolyline;
        } else {
            Dijsktra da = dijsktraService.selectDijsktraByWeightNumList(sa);
            Point first = pointService.selectPointById(da.getPointId());
            for (int i = 1;i < larrs.size();i++) {
                Dijsktra db = dijsktraService.selectDijsktraByWeightNumList(larrs.get(i));
                Point second = pointService.selectPointById(db.getPointId());
                Aisle aisle = new Aisle();
                aisle.setBeginPointX(first.getPointX());
                aisle.setBeginPointY(first.getPointY());
                aisle.setEndPointX(second.getPointX());
                aisle.setEndPointY(second.getPointY());
                first = second;
                Polyline p = new Polyline();
                p.setStyle("solid");
                p.setWeight(4);
                p.setColor("#f00");
                p.setOpacity(0.6);
                List<String> ls = new ArrayList<String>();
                ls.add(aisle.getBeginPointX() + "|" + aisle.getBeginPointY());
                ls.add(aisle.getEndPointX() + "|" + aisle.getEndPointY());
                p.setPoints(ls);
                lpolyline.add(p);
            }
        }
        return lpolyline;
    }
    /**
     * 根据角色及预案下发文档
     */
    @PostMapping("/outplan")
    @ResponseBody
    public AjaxResult outplan(PlanView planView) {
        /*List<PlanRole> lpr = planRoleService.selectPlanRoleListByPlanId(planView.getPlanId());
        for (PlanRole pr: lpr) {
            String url = pr.getUrl();
        }*/
        //传递前台说明不可执行
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("bool","false");
        return ajaxResult;
    }
    @GetMapping("/openSip/{sid}")
    public String main(@PathVariable("sid") String pid, ModelMap mmap) {
        //mmap.put("sid", sid);
        return "shebeiCall" ;
    }
    @GetMapping("/openSip/expert")
    public String expert(ModelMap mmap) {
        //mmap.put("sid", sid);
        return "expert" ;
    }
    @GetMapping("/lin")
    public String lin(ModelMap mmap) {
        //mmap.put("sid", sid);
        return "public/call" ;
    }
    @PostMapping("/sp")
    @ResponseBody
    public AjaxResult sp(PlanView planView) {
        //Shebei s = shebeiService.selectShebeiById(planView.getSid());
        AjaxResult ajaxResult = new AjaxResult();
        HashMap map = new HashMap<String, Object>();
        map.put("hostId", planView.getSid());
        map.put("cameraStatus", "1");
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "camera",json);//("http://192.168.4.101:8080/yjjy/tcp/camera", json);
        log.info(data);
        json = JSONObject.parseObject(data);
        json = JSONObject.parseObject(json.get("data").toString());
        if (!"1".equals(json.get("resultStatus").toString())) {
            //传递前台说明不可执行
            ajaxResult.put("bool","false");
        } else {
            ajaxResult.put("bool","true");
        }
        return ajaxResult;
    }
    @PostMapping("/spe")
    @ResponseBody
    public AjaxResult spe(PlanView planView) {
        //Shebei s = shebeiService.selectShebeiById(planView.getSid());
        AjaxResult ajaxResult = new AjaxResult();
        HashMap map = new HashMap<String, Object>();
        map.put("hostId", planView.getSid());
        map.put("cameraStatus", "0");
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "camera",json);//("http://192.168.4.101:8080/yjjy/tcp/camera", json);
        log.info(data);
        json = JSONObject.parseObject(data);
        json = JSONObject.parseObject(json.get("data").toString());
        if (!"1".equals(json.get("resultStatus").toString())) {
            //传递前台说明不可执行
            ajaxResult.put("bool","false");
        } else {
            ajaxResult.put("bool","true");
        }
        return ajaxResult;
    }
    @PostMapping("/jc")
    @ResponseBody
    public AjaxResult jc(PlanView planView) {
        //给所有设备发送解除险情
        List<Shebei> jc = shebeiService.selectShebeiList(null);
        List<String> jcsbids = new ArrayList<String>();
        for (Shebei sids: jc) {
            jcsbids.add(sids.getId());
        }
        Object idsJSON = JSONArray.toJSON(jcsbids);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hostIds", idsJSON);
        JSONObject json =new JSONObject(map);
        log.info(json);
        String data = HttpUtils.sendPost(MyStatic.url + "rmovedanger",json);
        log.info(data);
        json = JSONObject.parseObject(data);
        String msg = json.get("msg").toString();
        AjaxResult ajaxResult = new AjaxResult();
        if (!"解除险情发送完成".equals(msg)) {
            ajaxResult.put("bool","false");
        } else {
            ajaxResult.put("bool","true");
        }
        return ajaxResult;
    }
    @PostMapping("/num")
    @ResponseBody
    public AjaxResult num(PlanView planView) {
        Shebei s = shebeiService.selectShebeiById(planView.getSid());
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("bool","true");
        ajaxResult.put("num", s.getNumId());
        return ajaxResult;
    }
}