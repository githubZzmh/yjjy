package cn.com.ctrl.yjjy.project.rescue.trigger.controller;

import cn.com.ctrl.yjjy.common.utils.http.HttpUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.basis.planGroup.domain.PlanGroup;
import cn.com.ctrl.yjjy.project.basis.planGroup.service.IPlanGroupService;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.project.control.plan.domain.ShebeiPoints;
import cn.com.ctrl.yjjy.project.control.plan.service.IPointService;
import cn.com.ctrl.yjjy.project.control.plan.service.IShebeiPointsService;
import cn.com.ctrl.yjjy.project.rescue.aisle.domain.Aisle;
import cn.com.ctrl.yjjy.project.rescue.aisle.service.IAisleService;
import cn.com.ctrl.yjjy.project.rescue.trigger.tool.JpgEdit;
import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import cn.com.ctrl.yjjy.project.system.user.entity.Marker;
import cn.com.ctrl.yjjy.project.system.user.entity.PlanView;
import cn.com.ctrl.yjjy.project.system.user.entity.Polyline;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
import cn.com.ctrl.yjjy.project.tool.MyStatic;
import cn.com.ctrl.yjjy.project.tool.map.controller.DijsktraController;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.project.tool.map.service.IDijsktraService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * 一键触发
 *
 * @author yjjy
 */
@Log4j2
@Controller
@RequestMapping("/rescue/trigger")
public class TriggerController extends BaseController {
    private String prefix = "rescue/trigger" ;
    //通道
    @Autowired
    private IAisleService aisleService;
    //标点
    @Autowired
    private IPointService pointService;
    //预案
    @Autowired
    private IPlanService planService;
    //标点矩阵
    @Autowired
    private IDijsktraService dijsktraService;
    //设备
    @Autowired
    private IShebeiService shebeiService;
    //设备标点地图
    @Autowired
    private IShebeiPointsService shebeiPointsService;
    @Autowired
    private IPlanGroupService planGroupService;
    /**
     * 加载百度地图
     * @param mmap 返回map
     * @return 页面
     */
    @RequiresPermissions("rescue:trigger:view")
    @GetMapping()
    public String Calculation(ModelMap mmap) {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        //设置通道
        List<Aisle> laisle = aisleService.selectAisleList(null);
        List<Polyline> lpolyline = new ArrayList<Polyline>();
        int i = 0;
        for (Aisle aisle: laisle){
            Polyline p = new Polyline();
            p.setStyle("solid");
            p.setWeight(4);
            if("0".equals(aisle.getStatus()) || "可用".equals(aisle.getStatus())) {
                //可用
                p.setColor("#0f0");
            } else if ("1".equals(aisle.getStatus()) || "不可用".equals(aisle.getStatus())){
                //不可用
                p.setColor("#00f");
            } else {
                //占用
                p.setColor("#f00");
                i += 1;
            }
            p.setOpacity(0.6);
            List<String> ls = new ArrayList<String>();
            ls.add(decimalFormat.format(aisle.getBeginPointX()) + "|" + decimalFormat.format(aisle.getBeginPointY()));
            ls.add(decimalFormat.format(aisle.getEndPointX()) + "|" + decimalFormat.format(aisle.getEndPointY()));
            p.setPoints(ls);
            lpolyline.add(p);
        }
        mmap.put("color", i);
        Object plPoints = JSONArray.toJSON(lpolyline);
        mmap.put("plPoints",plPoints);
        //设置预案
        List<Plan> lplan = planService.selectPlanList(null);
        Object olplan = JSONArray.toJSON(lplan);
        mmap.put("plans",olplan);
        //
        List<Shebei> ls = shebeiService.selectShebeiList(null);
        Object olplanls = JSONArray.toJSON(ls);
        mmap.put("planIdfzls",olplanls);
        //设置标点
        List<Point> lp = pointService.selectPointList(null);
        List<Marker> lm = new ArrayList<Marker>();
        for (Point point: lp){
            Marker m = new Marker();
            m.setSid(point.getId());
            m.setTitle(point.getNumId());
            m.setContent(point.getName());
            m.setPoint(decimalFormat.format(point.getPointX()) + "|" + decimalFormat.format(point.getPointY()));
            m.setIconW(11);
            m.setIconH(16);
            m.setIconL(-5);
            m.setIconT(-6);
            m.setIconX(6);
            m.setIconLB(14);
            m.setType(point.getType());
            lm.add(m);
        }
        Object markerArr = JSONArray.toJSON(lm);
        mmap.put("markerArr",markerArr);
        return prefix + "/trigger" ;
    }
    /**
     * 更改通道状态
     */
    @PostMapping("/aisleStatus")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(PlanView planView) {
        String[] opSplit = planView.getSid().split("\\|");
        String[] edSplit = planView.getPlanId().split("\\|");
        Aisle aisle = new Aisle();
        aisle.setBeginPointX(Double.valueOf(opSplit[1]));
        aisle.setBeginPointY(Double.valueOf(opSplit[0]));
        aisle.setEndPointX(Double.valueOf(edSplit[1]));
        aisle.setEndPointY(Double.valueOf(edSplit[0]));
        List<Aisle> laisle = aisleService.selectAisleList(aisle);
        aisle = laisle.get(0);
        if("0".equals(aisle.getStatus()) || "可用".equals(aisle.getStatus())){
            aisle.setStatus("1");
        } else {
            aisle.setStatus("0");
        }
        Point p1 = new Point();
        Point p2 = new Point();
        p1.setPointX(Double.valueOf(opSplit[1]));
        p1.setPointY(Double.valueOf(opSplit[0]));
        p2.setPointX(Double.valueOf(edSplit[1]));
        p2.setPointY(Double.valueOf(edSplit[0]));
        List<Point> lp1 = pointService.selectPointList(p1);
        List<Point> lp2 = pointService.selectPointList(p2);
        //查找标点num
        Dijsktra d1 = new Dijsktra();
        Dijsktra d2 = new Dijsktra();
        d1.setPointId(lp1.get(0).getId());
        d2.setPointId(lp2.get(0).getId());
        List<Dijsktra> ld1 = dijsktraService.selectDijsktraList(d1);
        List<Dijsktra> ld2 = dijsktraService.selectDijsktraList(d2);
        d1 = ld1.get(0);
        d2 = ld2.get(0);
        int yd1 = Integer.parseInt(d1.getWeightNum());
        int yd2 = Integer.parseInt(d2.getWeightNum());
        //修改连域
        String[] larr1 = d1.getLarr().split(",");
        String[] larr2 = d2.getLarr().split(",");
        //旧连域，修改点
        List<Integer> li1 = arrToList(larr1, yd2);
        List<Integer> li2 = arrToList(larr2, yd1);
        StringBuffer sbf1 = new StringBuffer();
        StringBuffer sbf2 = new StringBuffer();
        for (int i: li1) {
            sbf1.append(i + ",");
        }
        sbf1.deleteCharAt(sbf1.length() - 1);
        for (int i: li2) {
            sbf2.append(i + ",");
        }
        sbf2.deleteCharAt(sbf2.length() - 1);
        d1.setLarr(sbf1.toString());
        d2.setLarr(sbf2.toString());
        dijsktraService.updateDijsktra(d1);
        dijsktraService.updateDijsktra(d2);
        //查询新全域
        String qu1 = selectDijsktra(yd1);
        String qu2 = selectDijsktra(yd2);
        //两新全域一致
        if (qu1.equals(qu2)) {
            //更新全域为新全域
            upall(qu1);
            //查询全域原点
            List<Dijsktra> count = dijsktraService.selectDijsktraCount(qu1);
            //单数原点无操作,复数原点删除非第一位的原点
            int cs = count.size();
            for (int i = 1;i < cs;i++){
                d1 = count.get(i);
                d1.setOpstatus("0");
                dijsktraService.updateDijsktra(d1);
            }
        } else {
            //更新两矩阵新全域
            upall(qu1);
            upall(qu2);
            //查询全域原点
            List<Dijsktra> count1 = dijsktraService.selectDijsktraCount(qu1);
            List<Dijsktra> count2 = dijsktraService.selectDijsktraCount(qu2);
            //有原点无操作,无原点设全域第一为原点
            upyd(count1, qu1);
            upyd(count2, qu2);
        }
        return toAjax(aisleService.updateAisle(aisle));
    }
    /**
     * 旧连域，修改点
     * @param larr 旧连域
     * @param yd 修改点
     * @return 新连域
     */
    private List<Integer> arrToList (String[] larr, int yd){
        List<Integer> li = new ArrayList<Integer>();
        boolean flg = false;
        boolean flgg = false;
        for (String arr: larr) {
            int i = Integer.valueOf(arr);
            if (yd > i) {
                flg = true;
            } else {
                flg = flgg;
            }
            if (i != yd) {
                if (!flg) {
                    li.add(yd);
                    flgg = true;
                }
                li.add(i);
            } else {
                flgg = true;
            }
        }
        if (!flgg && yd > li.get(li.size() - 1)) {
            li.add(yd);
        }
        return li;
    }
    /**
     * 查询全域
     * @param yd 查询点
     * @return 全域
     */
    private String selectDijsktra(int yd){
        //查询原点连域编号中的矩阵编号列表
        List<Dijsktra> ld = dijsktraService.selectDijsktraById(String.valueOf(yd));
        //声明连域集合组
        Set<Integer> si = new TreeSet<Integer>();
        //第一次查询结果去重
        for (Dijsktra d: ld){
            String[] larr = d.getLarr().split(",");
            for (String arr: larr){
                si.add(Integer.valueOf(arr));
            }
        }
        if(si.size()==1){
            return String.valueOf(yd);
        } else{
            //查询名单
            List<Integer> li = new ArrayList<Integer>();
            //写入查询名单
            for(Iterator iter = si.iterator(); iter.hasNext(); ) {
                li.add((Integer)iter.next());
            }
            boolean flg = false;
            for (;!flg;) {
                //获取新查询名单
                List<Integer> nli = addList(li);
                if (li.size()==nli.size()){
                    //名单查询结束
                    flg = true;
                }else {
                    //更新查询名单
                    li = nli;
                    //继续按照名单查询
                    flg = false;
                }
            }
            StringBuffer sbu = new StringBuffer();
            for (Integer it: li) {
                sbu.append(it + ",");
            }
            sbu.deleteCharAt(sbu.length() - 1);
            return sbu.toString();
        }
    }
    /**
     * 更新全域为新全域
     * @param qu 新全域
     */
    private void upall(String qu) {
        String[] nqu = qu.split(",");
        for (String g: nqu) {
            Dijsktra d = new Dijsktra();
            d.setWeightNum(g);
            List<Dijsktra> ld = dijsktraService.selectDijsktraList(d);
            d = ld.get(0);
            d.setAlarr(qu);
            dijsktraService.updateDijsktra(d);
        }
    }
    /**
     * 获取新查询名单
     * @param li 查询名单
     * @return li 新查询名单
     */
    private List<Integer> addList(List<Integer> li){
        //声明连域集合组
        Set<Integer> si = new TreeSet<Integer>();
        //设置连域集合组
        for (Integer i: li){
            si.add(i);
        }
        //循环名单查询
        for (Integer i : li) {
            //查询矩阵编号列表
            List<Dijsktra> ld = dijsktraService.selectDijsktraById(i.toString());
            //循环列表
            for (Dijsktra d: ld){
                String[] larr = d.getLarr().split(",");
                //设置连域集合组
                for (String arr:larr){
                    si.add(Integer.valueOf(arr));
                }
                //名单与连域集合组长度不一致
                if (si.size()!=li.size()){
                    li = new ArrayList<Integer>();
                    //写入查询名单
                    for(Iterator iter = si.iterator(); iter.hasNext(); ) {
                        li.add((Integer)iter.next());
                    }
                    return li;
                }
            }
        }
        return li;
    }
    /**
     * 设置原点数量
     * @param count 全域原点
     * @param qu 新全域
     */
    private void upyd(List<Dijsktra> count, String qu) {
        int cs = count.size();
        if (cs > 0) {
            if (cs > 1) {
                for (int i = 1;i < cs;i++){
                    Dijsktra d = count.get(i);
                    d.setOpstatus("0");
                    dijsktraService.updateDijsktra(d);
                }
            }
        } else {
            //查找全域第一num
            String[] larr = qu.split(",");
            Dijsktra d = new Dijsktra();
            d.setWeightNum(larr[0]);
            count = dijsktraService.selectDijsktraList(d);
            //设值原点
            d = count.get(0);
            d.setOpstatus("1");
            //写入
            dijsktraService.updateDijsktra(d);
        }
    }
    /**
     * 计算路径
     */
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult updateSave(PlanView planView) throws IOException {
        //获得开始位置
        List<Dijsktra> ldop = dijsktraService.selectDijsktraOp(planView.getSid());
        Dijsktra ksbd = ldop.get(0);
        //获得结束位置列表
        List<Dijsktra> lded = dijsktraService.selectDijsktraOp(planView.getPlanId());
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
                i = ld.size();
            }
        }
        if (list < 0) {
            //传递前台说明不可执行
            return AjaxResult.error(9, "当前位置无法进行路径引导");
        }
        //获得有效全域列表
        String arr = ld.get(list).getAlarr();
        //取得有效全域组
        List<String> larr = Arrays.asList(arr.split(","));
        //演算路程矩阵
        //double[][] darr = dijsktra(arr);
        double[][] darr = dijsktraService.selectMYJZ(arr);
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
        //途径点编号集合
        List<String> larrs = new ArrayList<String>();
        String[] sarr = path.split("->");
        for (String ss: sarr) {
            int ssi = Integer.valueOf(ss);
            //定位途径点
            larrs.add(larr.get(ssi));
        }
        String tdlb = "";
        for (int i = 1;i < larrs.size();i++) {
            tdlb += larrs.get(i - 1) + "至" + larrs.get(i) + ",";
        }
        List<Aisle> la = aisleService.selectAisleByIds(tdlb.substring(0, tdlb.length() - 1));
        for (int i = 0;i < la.size();i++) {
            la.get(i).setStatus("2");
            aisleService.updateAisle(la.get(i));
        }
        /*
        * 查询有效设备
        * 演算至目标点路径
        * 当前设备至路径中下一设备路径
        * 下发
        */
        Dijsktra dijsktra = ksbd;
        Shebei shebei = new Shebei();
        shebei.setStatus("1");
        List<Shebei> lists = shebeiService.selectShebeiList(shebei);
        List<String> ls = new ArrayList<String>();
        for (int i = 0;i < lists.size();i++) {
            //获得开始位置
            ldop = dijsktraService.selectDijsktraOp(lists.get(i).getId());
            ksbd = ldop.get(0);
            //有效全域在列表中的位置
            list = -1;
            //循环全域列表
            for (int ii = 0;ii < ld.size();ii++) {
                Dijsktra d = ld.get(ii);
                //当前全域列表与开始位置全域一致
                if (d.getAlarr().equals(ksbd.getAlarr()) && jsbd.getAlarr().equals(ksbd.getAlarr())) {
                    //标记当前全域在列表中的位置
                    list = ii;
                    ii = ld.size();
                }
            }
            if (list < 0) {
                //不可执行
                break;
            }
            //获得有效全域列表
            arr = ld.get(list).getAlarr();
            //取得有效全域组
            larr = Arrays.asList(arr.split(","));
            for (int ii = 0;ii < larr.size();ii++) {
                if (ksbd.getWeightNum().equals(larr.get(ii))) {
                    op = ii;
                }
                if (jsbd.getWeightNum().equals(larr.get(ii))) {
                    ed = ii;
                }
            }
            path = DijsktraController.dijsktra2(darr, op, ed);
            //途径点编号集合
            larrs = new ArrayList<String>();
            sarr = path.split("->");
            for (String ss: sarr) {
                int ssi = Integer.valueOf(ss);
                //定位途径点
                larrs.add(larr.get(ssi));
            }
            //标点矩阵编号larrs
            //lists设备列表
            String dqqd = "";
            for (int ii = 0;ii < larrs.size();ii++) {
                if (pointService.selectPointById(dijsktraService.selectDijsktraListByNums("'" + larrs.get(ii) + "'").get(0).getPointId()).getType().equals("1") || ii == larrs.size()-1) {
                    if ("".equals(dqqd)) {
                        dqqd += larrs.get(ii);
                    } else {
                        if (dqqd.equals(larrs.get(ii))) {
                            dqqd = "";
                            continue;
                        }
                        //dqqd += "至" + larrs.get(ii);
                        //dqqd += "至" + larrs.get(ii);
                        ShebeiPoints sp = shebeiPointsService.selectShebeiPointsById(dqqd);
                        Point pe = pointService.selectPointById(dijsktraService.selectDijsktraListByNums(larrs.get(ii)).get(0).getPointId());
                        //生成语音
                        String bpoint, epoint, year, month, day, time, minute, second, accident, route, name;
                        bpoint = shebeiService.selectShebeiById(dijsktra.getPointId()).getName();//dijsktra.getPointName();
                        epoint = pe.getName();
                        String[] bd = planService.selectPlanById(planView.getPId()).getName().split("事故");
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
                        //DijsktraController.mp3(bpoint, epoint, year, month, day, time, minute, second, accident, route, name);
                        String url = "";
                        String url_ = "";
                        for (int i_ = 1;i_ < larrs.size();i_++) {
                            String a = larrs.get(i_ - 1) + "至" + larrs.get(i_);
                            Aisle aisle = new Aisle();
                            aisle.setName(a);
                            List<Aisle> aisles = aisleService.selectAisleList(aisle);
                            aisle = aisles.get(0);
                            if (url_.equals(aisle.getKname())) {
                                continue;
                            } else {
                                url_ = aisle.getKname();
                                url += url_ + "至";
                            }
                        }
                        DijsktraController.mp3_(bpoint, epoint, year, month, day, time, minute, second, accident, planView.getFjId(), url, route, name);
                        DijsktraController.testt(sp.getMapUrl(), sp.getMapName(), url.substring(0,url.length()-1));
                        //发送
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("hostId", ksbd.getPointId());
                        map.put("mapName", sp.getMapName());
                        map.put("mapUrl", "C:\\fakepath\\ttt\\"+ sp.getMapName());
                        map.put("broadName", sp.getBroadName());
                        map.put("broadUrl", sp.getBroadUrl() + sp.getBroadName());
                        JSONObject json =new JSONObject(map);
                        log.info(json);
                        ls.add(json.toString());
                        dqqd = larrs.get(ii);
                        break;
                    }
                }
            }
        }
        if (ls.size() > 0) {
            DijsktraController.output("C:\\fakepath\\out\\yjjy.txt", ls);
        }
        return AjaxResult.success();
    }
    /**
     * 演算路程矩阵
     * @param alarr 全域字符串
     * @return 路程矩阵
     */
    private double[][] dijsktra (String alarr) {
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
                    dou[i][ii] = distance(a, b);
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
    private double distance (Point a, Point b) {
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
     * 取消救援
     */
    @PostMapping("/uver")
    @ResponseBody
    public AjaxResult uverSave(PlanView planView) {
        String pathname = "C:\\fakepath\\out\\yjjy.txt";
        File filename = new File(pathname);
        if (filename.exists()) {
            filename.delete();
        }
        aisleService.uverAisle();
        return AjaxResult.success();
    }
    /**
     * 救援下发
     */
    @PostMapping("/fuver")
    @ResponseBody
    public AjaxResult fuverSave(PlanView planView) throws IOException {
        String[] ids = planView.getSid().split(",");
        List<String> ls = DijsktraController.input("C:\\fakepath\\out\\yjjy.txt");
        for (String s: ls) {
            JSONObject json = JSONObject.parseObject(s);
            for (String id:ids) {
                if (json.get("hostId").equals(id)) {
                    log.info(json);
                    String data = HttpUtils.sendPost(MyStatic.url + "escape",json);
                    log.info(data);
                    json = JSONObject.parseObject(data);
                    String msg = json.get("msg").toString();
                    if (!"任务已下发".equals(msg)) {
                        return AjaxResult.error(9,"救援下发失败");
                    }
                }
            }
        }
        return AjaxResult.success();
    }
    /**
     * 解除救援
     */
    @PostMapping("/jyrem")
    @ResponseBody
    public AjaxResult jyremSave(PlanView planView) {
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
        if (!"任务已下发".equals(msg)) {
            return AjaxResult.error(9,"解除救援失败");
        }
        return uverSave(planView);
    }
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * 查找预案组
     */
    @PostMapping("/planIdsel")
    @ResponseBody
    public AjaxResult planIdsel(PlanView planView) {
        PlanGroup pg = new PlanGroup();
        pg.setPlanId(planView.getSid());
        List<PlanGroup> lpg = planGroupService.selectPlanGroupList(pg);
        String planIds = "";
        String names = "";
        for (PlanGroup pgs:lpg) {
            planIds += pgs.getId();
            names += pgs.getName();
        }
        AjaxResult json = new AjaxResult();
        json.put("msg" , "");
        json.put("code" , 0);
        json.put("planIds" , 0);
        json.put("names" , 0);
        return json;
    }
    /**
     * 图片处理
     */
    @PostMapping("/testt")
    @ResponseBody
    public AjaxResult testt(PlanView planView) {
        //目标图片路径
        String targetImg = "C:\\fakepath\\excape\\picture\\";
        String targetImg_ = "1to2.jpg";
        //水印文字
        String pressText = "测试水印文字第一行;测试水印文字第二行";//大伤脑筋非常v你尝尝n'c'sODcnisNcksdncjsdpncipsdvsdn
        //字体名称
        String fontName = "微软雅黑";
        //字体样式
        int fontStyle = Font.BOLD;
        //字体大小
        int fontSize = 36;
        //字体颜色
        Color color = Color.red;
        //x
        int x = 20;
        //y
        int y = 40;
        //透明度
        float alpha = 0.5f;
        JpgEdit.pressText(targetImg, targetImg_, pressText, fontName, fontStyle, fontSize, color, x, y, alpha);
        AjaxResult json = new AjaxResult();
        json.put("msg" , "");
        json.put("code" , 0);
        return json;
    }
}