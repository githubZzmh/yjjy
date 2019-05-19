package cn.com.ctrl.yjjy.project.system.user.controller;
import cn.com.ctrl.yjjy.common.utils.DateUtils;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatService;
import cn.com.ctrl.yjjy.project.basis.group.service.IShebeiCatShebeiService;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.project.control.plan.service.IPointService;
import cn.com.ctrl.yjjy.project.rescue.aisle.domain.Aisle;
import cn.com.ctrl.yjjy.project.rescue.aisle.service.IAisleService;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsCd;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsDate;
import cn.com.ctrl.yjjy.project.system.echarsDP.mapper.EchartsCdMapper;
import cn.com.ctrl.yjjy.project.system.echarsDP.service.EchartsCdService;
import cn.com.ctrl.yjjy.project.system.menu.domain.Menu;
import cn.com.ctrl.yjjy.project.system.user.domain.User;
import cn.com.ctrl.yjjy.framework.config.YjjyConfig;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.project.system.menu.service.IMenuService;
import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import cn.com.ctrl.yjjy.project.system.user.entity.EchartsSimple;
import cn.com.ctrl.yjjy.project.system.user.entity.Marker;
import cn.com.ctrl.yjjy.project.system.user.entity.PlanView;
import cn.com.ctrl.yjjy.project.system.user.entity.Polyline;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanMapService;
import cn.com.ctrl.yjjy.project.system.user.service.IPlanService;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.project.tool.map.service.IDijsktraService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Radar;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
/**
 * 首页 业务处理
 *
 * @author zzmh
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IAisleService aisleService;
    @Autowired
    private IPlanService planService;
    @Autowired
    private IPlanMapService planMapService;
    @Autowired
    private IPointService pointService;
    @Autowired
    private IShebeiCatShebeiService shebeiCatShebeiService;
    @Autowired
    private IDijsktraService dijsktraService;
    @Autowired
    private YjjyConfig yjjyConfig;
    @Autowired
    private EchartsCdService echartsCdService;
    @Autowired
    private IShebeiCatService shebeiCatService;
    @Autowired
    private IShebeiService shebeiService;
    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        User user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus" , menus);
        mmap.put("user" , user);
        mmap.put("copyrightYear" , yjjyConfig.getCopyrightYear());
        return "index" ;
    }

    /**
     * 加载百度地图
     * @param mmap 返回map
     * @return 页面
     */
    @GetMapping("/system/map")
    public String map(ModelMap mmap) {
        List<Aisle> laisle = aisleService.selectAisleList(null);
        List<Polyline> lpolyline = new ArrayList<Polyline>();
        for (Aisle aisle: laisle){
            Polyline p = new Polyline();
            p.setStyle("solid");
            p.setWeight(4);
            if("0".equals(aisle.getStatus())) {
                p.setColor("#0f0");
            } else {
                p.setColor("#0ff");
            }
            p.setOpacity(0.6);
            List<String> ls = new ArrayList<String>();
            ls.add(aisle.getBeginPointX() + "|" + aisle.getBeginPointY());
            ls.add(aisle.getEndPointX() + "|" + aisle.getEndPointY());
            p.setPoints(ls);
            lpolyline.add(p);
        }
        Object plPoints = JSONArray.toJSON(lpolyline);
        mmap.put("plPoints",plPoints);
        List<Plan> lplan = planService.selectPlanList(null);
        Object olplan = JSONArray.toJSON(lplan);
        mmap.put("plans",olplan);
        List<Point> lp = pointService.selectPointList(null);
        List<Marker> lm = new ArrayList<Marker>();
        for (Point point: lp){
            Marker m = new Marker();
            m.setSid(point.getId());
            m.setTitle(point.getNumId());
            m.setContent(point.getName());
            m.setPoint(point.getPointX() + "|" + point.getPointY());
            m.setIconW(11);
            m.setIconH(16);
            m.setIconL(-5);
            m.setIconT(-6);
            m.setIconX(6);
            m.setIconLB(14);
            lm.add(m);
        }
        Object markerArr = JSONArray.toJSON(lm);
        mmap.put("markerArr",markerArr);
        return "map";
    }
    // echarts
    @GetMapping("/system/echarts")
    public String echarts(ModelMap mmap){
        return "echarts";
    }
    /*
     * 获取雷达图 json数据
     * */
    @GetMapping("/system/getRadarOption")
    @ResponseBody
    public Option getRadarOption() {
        Option option = new Option();
        Title title = new Title();
        title.setText("事故预案统计雷达图");
        option.title(title);
        option.tooltip();
        List<String> ls = new ArrayList<>();
        ls.add("当月事故预案");
        ls.add("累计事故预案");
        option.legend().data(ls);
        Radar radar = new Radar();
        radar.name().textStyle().setColor("#fff");
        radar.name().textStyle().backgroundColor("#999");
        radar.name().textStyle().borderRadius(3);
        int[] ai = {3, 4};
        radar.name().textStyle().padding(ai);
        List<EchartsSimple> les = planMapService.selectPlanMapList(null);
        List<EchartsSimple> les2 = planMapService.selectPlanMapById(null);
        Integer[] v1 = new Integer[les.size()];
        Integer[] v2 = new Integer[les2.size()];
        List<JSONObject> seriesList = new ArrayList< JSONObject>();
        for (int i = 0;i < les.size();i++){
            v1[i] = Integer.valueOf(les.get(i).getValue());
            v2[i] = Integer.valueOf(les2.get(i).getValue());
        }
        int max = Collections.max(Arrays.asList(v1));
        max = (max/5+1) * 5;
        for (int i = 0;i < les.size();i++){
            Radar.Indicator ind = new Radar.Indicator();
            ind.max(max);
            ind.name(les.get(i).getName());
            radar.indicator().add(ind);
        }
        option.radar(radar);
        Series series = new Series(){};
        series.setName("预算 vs 开销");
        series.type(SeriesType.radar);
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("value", v1);
        jsonObject2.put("value", v2);
        jsonObject1.put("name","累计事故预案");
        jsonObject2.put("name","当月事故预案");
        JSONObject[] arr = new JSONObject[2];
        arr[0] = jsonObject1;
        arr[1] = jsonObject2;
        series.data(arr);
        List<Series> seriess = new ArrayList<Series>();
        seriess.add(series);
        option.series(seriess);
        return option;
    }
    /*
     * 获取饼图 json数据
     * */
    @GetMapping("/system/getSimpleOption")
    @ResponseBody
    public Option getSimpleOption() {
        Option option = new Option();
        Title title = new Title();
        title.setText("设备统计");
        title.setSubtext("分组计数");
        title.setX("center");
        option.title(title);
        option.tooltip().trigger(Trigger.item);
        option.legend().setOrient(Orient.vertical);
        option.legend().left("left");
        List<EchartsSimple> les = shebeiCatShebeiService.selectEchartsSimple(null);
        String[] legendName = new String[les.size()];
        List< JSONObject> seriesList = new ArrayList< JSONObject>();
        for (int i = 0;i < les.size();i++){
            legendName[i] = les.get(i).getName();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", les.get(i).getValue());
            jsonObject.put("name",les.get(i).getName());
            seriesList.add(jsonObject);
        }
        option.legend().data(legendName);
        List<Series> seriess = new ArrayList<Series>();
        Series series = new Series(){};
        series.setName("设备");
        series.type(SeriesType.pie);
        JSONObject[] arr = new JSONObject[seriesList.size()];
        seriesList.toArray(arr);
        series.data(arr);
        seriess.add(series);
        option.series(seriess);
        return option;
    }
    /**
     * 更改通道状态
     */
    @PostMapping("/system/aisleStatus")
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
        if("0".equals(aisle.getStatus())){
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
        String qu1 = dijsktra(yd1);
        String qu2 = dijsktra(yd2);
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
     * 查询全域
     * @param yd 查询点
     * @return 全域
     */
    private String dijsktra(int yd){
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

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version" , yjjyConfig.getVersion());
        Map map = new HashMap();
        //所有设备
        map.put("status","");
        Integer allCounts = shebeiService.selectShebeiCountByStatus(map);
        //在线设备
        map.put("status","0");
        Integer onCounts = shebeiService.selectShebeiCountByStatus(map);
        //播放音乐设备设备
        map.put("status","2");
        Integer musicCounts = shebeiService.selectShebeiCountByStatus(map);
        //在线广播设备
        map.put("status","3");
        Integer guangboCounts = shebeiService.selectShebeiCountByStatus(map);

        mmap.put("allCounts",allCounts);
        mmap.put("onCounts",onCounts);
        mmap.put("musicCounts",musicCounts);
        mmap.put("guangboCounts",guangboCounts);
        return "echarts2" ;
    }

    @GetMapping("/system/qushi")
    @ResponseBody
    public Map qushi(ModelMap mmap){
        Map map = new HashMap();
        Object[] name = new Object[30];
        Integer[] count = new Integer[30];
        Integer[] outage = new Integer[30];
//        List<EchartsDate> listEchartsDate = new ArrayList<>();
        Map parmp = new HashMap();
        for(int i=30;i>0;i--){
//            EchartsDate echartsDate = new EchartsDate();
            Object str = DateUtils.getDaysAgo(i);
            name[i-1] = str;
            parmp.put("num",i);
            parmp.put("status","1");
            Integer data_hujiao = shebeiService.selectEveryDayCount(parmp);
            count[i-1] = data_hujiao;
            parmp.put("status","0");
            Integer data_dangji = shebeiService.selectEveryDayCount(parmp);
            outage[i-1] = data_dangji;
//            listEchartsDate.add(echartsDate);
        }


//        List<EchartsCd> echartsCdList = echartsCdService.selectEchartsCd(null);
//
//        for(int i = 0;i< 12;i++){
//            name[i] = echartsCdList.get(i).getDevice();
//            count[i] = Integer.parseInt(echartsCdList.get(i).getData());
//            outage[i] = Integer.parseInt(echartsCdList.get(i).getData_());
//        }
        map.put("name",name);
        map.put("count",count);
        map.put("outage",outage);
        return map;
    }


    @GetMapping("/system/shebeiCat")
    @ResponseBody
    public Map shebeiCat(ModelMap mmap){
        Map map = new HashMap();
        //查询设备分类
        ShebeiCat cat = new ShebeiCat();
        List<ShebeiCat> catList = shebeiCatService.selectShebeiCatList(cat);
        String[] catName = new String[catList.size()];
        Integer[] shebeiCounts = new Integer[catList.size()];
        for(int i = 0;i< catList.size();i++){
            catName[i] = catList.get(i).getName();
            Map parmap = new HashMap();
            parmap.put("shebeiCatId",catList.get(i).getId());
            Integer counts = shebeiCatShebeiService.selectshebeiCountByCatId(parmap);
            shebeiCounts[i] = counts;
        }
        map.put("catName",catName);
        map.put("shebeiCounts",shebeiCounts);
        return map;
    }

}