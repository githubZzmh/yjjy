package cn.com.ctrl.yjjy.project.system.echarsDP.controller;
import cn.com.ctrl.yjjy.common.utils.DateUtils;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.service.IShebeiService;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.*;
import cn.com.ctrl.yjjy.project.system.echarsDP.service.EchartsCdService;
import cn.com.ctrl.yjjy.project.system.echarsDP.service.EchartsDateService;
import cn.com.ctrl.yjjy.project.system.echarsDP.service.EchartsListService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 大屏
 *
 * @author zzmh
 */
@Log4j2
@Controller
@RequestMapping("/dp")
public class EcharsController extends BaseController {
    @Autowired
    private EchartsDateService echartsDateService;
    @Autowired
    private EchartsListService echartsListService;
    @Autowired
    private EchartsCdService echartsCdService;
    @Autowired
    private IShebeiService shebeiService;
    @GetMapping("/echarts")
    public String echarts() {
        String url = "redirect:http://localhost:9998/yjjy/yingjiCount/index.html";
        return url;
    }
    @ResponseBody
    @RequestMapping("/getMySeat")
    public String getMySeatSuccess(@RequestParam("callback") String callback){
        EchartsDP ed = new EchartsDP();
        //近30天设备及故障数据统计---------------------------------------------
//        List<EchartsDate> listEchartsDate = echartsDateService.selectEchartsDate();
        List<EchartsDate> listEchartsDate = new ArrayList<>();
        Map parmp = new HashMap();
        for(int i=30;i>0;i--){
            EchartsDate echartsDate = new EchartsDate();
            String str = DateUtils.getDaysAgo(i);
            echartsDate.setDate(str);
            parmp.put("num",i);
            parmp.put("status","1");
            String data_hujiao = String.valueOf(shebeiService.selectEveryDayCount(parmp));
            echartsDate.setData_hujiao(data_hujiao);
            parmp.put("status","0");
            String data_dangji = String.valueOf(shebeiService.selectEveryDayCount(parmp));
            echartsDate.setData_dangji(data_dangji);
            listEchartsDate.add(echartsDate);
        }

        ed.setListEchartsDate(listEchartsDate);
        //---------------------------------------------
        EchartsDevice eddd = new EchartsDevice();

        Map param = new HashMap();
        //所有设备数量
        param.put("status","");
        Integer allCounts = shebeiService.selectShebeiCountByStatus(param);
        //在线设备
        param.put("status","0");
        Integer onCounts = shebeiService.selectShebeiCountByStatus(param);
        //设备在线率
        BigDecimal percent = (new BigDecimal(onCounts).multiply((new BigDecimal(100)))).divide(new BigDecimal(allCounts),2, BigDecimal.ROUND_HALF_UP);
        //设备事故率

        eddd.setDevice_all(allCounts.toString());
        eddd.setDevice_online(onCounts.toString());
        eddd.setRate_online(percent.toString());
        eddd.setRate_crash((new BigDecimal(100)).subtract(percent).toString());
        ed.setEchartsDevice(eddd);
        //---------------------------------------------
        //设备状态列表
        List<Shebei> listEchartsList = shebeiService.selectShebeiDp();
        ed.setListEchartsList(listEchartsList);
        //设备呼叫宕机排名---------------------------------------------
        List<EchartsCd> listEchartsCd = new ArrayList<>();
        List<Shebei> shebeiList = shebeiService.selectShebeiList(null);
        param.put("num","");
        for(Shebei s: shebeiList){
            EchartsCd echartsCd = new EchartsCd();
            echartsCd.setDevice(s.getName());
            param.put("shebeiId",s.getId());
            parmp.put("status","1");
            String hujiao = String.valueOf(shebeiService.selectEveryDayCount(parmp));
            echartsCd.setData(hujiao);
            parmp.put("status","0");
            String dangji = String.valueOf(shebeiService.selectEveryDayCount(parmp));
            echartsCd.setData_(dangji);
            listEchartsCd.add(echartsCd);
        }
//        List<EchartsCd> listEchartsCd = echartsCdService.selectEchartsCd(null);
        List<String> device = new ArrayList<String>();
        List<String> data = new ArrayList<String>();
        List<String> data_ = new ArrayList<String>();
        for (EchartsCd ec: listEchartsCd){
            device.add(ec.getDevice());
            data.add(ec.getData());
            data_.add(ec.getData_());
        }
        EchartsCall echartsCall = new EchartsCall();
        echartsCall.setDevice(device);
        echartsCall.setData(data);
        ed.setEchartsCall(echartsCall);
        EchartsDrop echartsDrop = new EchartsDrop();
        echartsDrop.setDevice(device);
        echartsDrop.setData_(data_);
        ed.setEchartsDrop(echartsDrop);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ed",ed);
        JSONObject json =new JSONObject(map);
        log.info(json);
        return callback+"("+json+")";
    }
}