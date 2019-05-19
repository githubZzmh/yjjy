package cn.com.ctrl.yjjy.project.rescue.calculation.controller;

import cn.com.ctrl.yjjy.common.utils.jacob.JacobUtils;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.project.control.plan.service.IPointService;
import cn.com.ctrl.yjjy.project.rescue.aisle.domain.Aisle;
import cn.com.ctrl.yjjy.project.rescue.aisle.service.IAisleService;
import cn.com.ctrl.yjjy.project.system.dict.domain.DictData;
import cn.com.ctrl.yjjy.project.system.dict.service.IDictDataService;
import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import cn.com.ctrl.yjjy.project.system.user.entity.Marker;
import cn.com.ctrl.yjjy.project.system.user.entity.PlanView;
import cn.com.ctrl.yjjy.project.system.user.entity.Polyline;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
import cn.com.ctrl.yjjy.project.tool.map.controller.DijsktraController;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.project.tool.map.service.IDijsktraService;
import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 路径计算
 *
 * @author yjjy
 */
@Controller
@RequestMapping("/rescue/calculation")
public class CalculationController extends BaseController {
    private String prefix = "rescue/calculation" ;
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
    @Autowired
    private IShebeiService shebeiService;
    @Autowired
    private IDictDataService dictDataService;
    /**
     * 加载百度地图
     * @param mmap 返回map
     * @return 页面
     */
    @RequiresPermissions("rescue:calculation:view")
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
        mmap.put("lm",lm);
        return prefix + "/calculation" ;
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
        List<String> larrs = new ArrayList<String>();
        String[] sarr = path.split("->");
        for (String ss: sarr) {
            int ssi = Integer.valueOf(ss);
            //定位途径点
            larrs.add(larr.get(ssi));
        }
        //100->11->8->2->1
        String tdlb = "";
        for (int i = 1;i < larrs.size();i++) {
            tdlb += larrs.get(i - 1) + "至" + larrs.get(i) + ",";
        }
        List<Aisle> la = aisleService.selectAisleByIds(tdlb.substring(0, tdlb.length() - 1));
        for (int i = 0;i < la.size();i++) {
            la.get(i).setStatus("2");
            aisleService.updateAisle(la.get(i));
        }
        //--------------------------------------------------------------------------------------------------------------
        String amane = "";
        String oamane = "oamane";
        String namane = "namane";
        String musicLD = "紧急疏散,";
        for (int i = 1;i < larrs.size();i++) {
            amane = larrs.get(i - 1) + "至" + larrs.get(i);
            for (int ii = 0;ii < la.size();ii++) {
                if (la.get(ii).getName().equals(amane)) {
                    namane = la.get(ii).getKname();
                    if (oamane.equals(namane)) {
                        break;
                    } else {
                        oamane = namane;
                    }
                    musicLD += la.get(ii).getKname() + "到";
                    break;
                }
            }
        }
        musicLD = musicLD.substring(0, musicLD.length() - 1);
        List<String> ls = new ArrayList<String>();
        ls.add(musicLD);
        DijsktraController.output2("C:\\fakepath\\jy\\jy.txt", ls);
        //JacobUtils.jacob("C:\\fakepath\\jy\\jy.txt");
        AjaxResult json = new AjaxResult();
        json.put("msg" , musicLD);
        json.put("code" , 0);
        return json;
    }
    /**
     * 计算路径播报
     */
    @PostMapping("/updatebb")
    @ResponseBody
    public AjaxResult updatebbSave(PlanView planView) throws IOException {
        JacobUtils.jacob("C:\\fakepath\\jy\\jy.txt");
        AjaxResult json = new AjaxResult();
        json.put("msg" , "");
        json.put("code" , 0);
        return json;
    }
    /**
     * 演算路程矩阵
     * @param alarr 全域字符串
     * @return 路程矩阵
     */
    private double[][] dijsktra (String alarr) {
        //查询全域标点列表
        /*Dijsktra d = new Dijsktra();
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
        return dou;*/
        Dijsktra d = new Dijsktra();
        d.setAlarr(alarr);
        List<Dijsktra> ld = dijsktraService.selectDijsktraList(d);
        double[][] dou = new double[ld.size()][ld.size()];
        for (int i = 0;i < ld.size();i++) {
            for (int ii = i;ii < ld.size();ii++) {
                if (i == ii) {
                    dou[i][ii] = 0;
                } else {
                    Point a = pointService.selectPointById(ld.get(i).getPointId());
                    Point b = pointService.selectPointById(ld.get(ii).getPointId());
                    double t = distance(a, b);
                    dou[i][ii] = t;
                    dou[ii][i] = t;
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
     * 释放路径
     */
    @PostMapping("/uver")
    @ResponseBody
    public AjaxResult uverSave(PlanView planView) {
        aisleService.uverAisle();
        return AjaxResult.success();
    }
    /**
     * 新增保存百度地图点
     */
    @RequiresPermissions("rescue:calculation:view")
    @Log(title = "百度地图点" , businessType = BusinessType.INSERT)
    @PostMapping("/addSave")
    @ResponseBody
    public AjaxResult addSave(Point point) {
        Boolean b = false;
        if ("1".equals(point.getType())) {
            Shebei shebei = shebeiService.selectShebeiById(point.getName());
            shebei.setPointX(point.getPointX());
            shebei.setPointY(point.getPointY());
            shebeiService.updateShebei(shebei);
            Point npoint = new Point();
            npoint.setId(shebei.getId());
            npoint.setType("1");
            npoint.setNumId(shebei.getNumId());
            npoint.setName(shebei.getName());
            npoint.setPointX(point.getPointX());
            npoint.setPointY(point.getPointY());
            return toAjax(pointService.insertPoint(npoint));
            /*List<Shebei> shebeiList = shebeiService.selectShebeiList(null);
            for (Shebei shebei: shebeiList) {
                if (shebei.getPointX().equals(point.getPointX()) && shebei.getPointY().equals(point.getPointY())) {
                    b = true;
                    return toAjax(pointService.insertPoint(point));
                }
            }
            if (!b) {
                return AjaxResult.error(1, "添加设备点失败，当前位置无设备信息");
            }*/
        }
        return toAjax(pointService.insertPoint(point));
    }
    /**
     * 新增保存百度地图通道
     */
    @RequiresPermissions("rescue:calculation:view")
    @Log(title = "百度地图通道" , businessType = BusinessType.INSERT)
    @PostMapping("/addSavePolyline")
    @ResponseBody
    public AjaxResult addSavePolyline(Aisle aisle) {
        Point p1 = new Point();
        p1.setId(aisle.getBeginPoint());
        /*StringTokenizer st1 = new StringTokenizer(aisle.getBeginPoint(),"|");
        p1.setPointX(Double.valueOf(st1.nextToken()));
        p1.setPointY(Double.valueOf(st1.nextToken()));
        StringTokenizer st2 = new StringTokenizer(aisle.getEndPoint(),"|");*/
        p1 = pointService.selectPointList(p1).get(0);
        Point p2 = new Point();
        p2.setId(aisle.getEndPoint());
        /*p2.setPointX(Double.valueOf(st2.nextToken()));
        p2.setPointY(Double.valueOf(st2.nextToken()));*/
        p2 = pointService.selectPointList(p2).get(0);
        aisle.setBeginPoint(p1.getId());
        aisle.setEndPoint(p2.getId());
        aisle.setName(p1.getNumId() + "至" + p2.getNumId());
        aisle.setStatus("0");
        return toAjax(aisleService.insertAisle(aisle));
        //return AjaxResult.error(1, "添加设备点失败，当前位置无设备信息");
    }
    /**
     * 标点窗口
     */
    @RequiresPermissions("rescue:calculation:view")
    @GetMapping("/edit/{id}")
    public String reject(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id" , id);
        return prefix + "/edit" ;
    }
    /**
     * 标点窗口保存
     */
    @Log(title = "标点窗口保存" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult rejectSave() {
        return toAjax(1);
    }
    /**
     * 释放路径
     */
    @PostMapping("/coefficient")
    @ResponseBody
    public AjaxResult coefficient(PlanView planView) {
        DictData data = new DictData();
        data.setDictType("coefficient");
        DictData dictData = dictDataService.selectDictDataList(data).get(0);
        List<Shebei> ls = shebeiService.selectShebeis();
        String ids = "";
        String vals = "";
        for (Shebei s: ls) {
            ids+=s.getId()+",";
            vals+=s.getNumId()+",";
        }
        AjaxResult json = new AjaxResult();
        json.put("msg" , "");
        json.put("code" , dictData.getDictValue());
        json.put("listid" , "q");
        json.put("listval" , "");
        if (!"".equals(ids)) {
            json.put("listid" , ids.substring(0,ids.length()-1));
            json.put("listval" , vals.substring(0,vals.length()-1));
        }
        return json;
    }
}