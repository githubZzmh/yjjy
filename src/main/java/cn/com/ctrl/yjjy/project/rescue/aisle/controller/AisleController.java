package cn.com.ctrl.yjjy.project.rescue.aisle.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.project.control.plan.service.IPointService;
import cn.com.ctrl.yjjy.project.rescue.region.domain.Region;
import cn.com.ctrl.yjjy.project.rescue.region.service.IRegionService;
import cn.com.ctrl.yjjy.project.system.dict.domain.DictData;
import cn.com.ctrl.yjjy.project.system.dict.service.IDictDataService;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.project.tool.map.service.IDijsktraService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.project.rescue.aisle.domain.Aisle;
import cn.com.ctrl.yjjy.project.rescue.aisle.service.IAisleService;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;

/**
 * 通道 信息操作处理
 *
 * @author yjjy
 * @date 2019-03-14
 */
@Data
@Log4j2
@Controller
@RequestMapping("/rescue/aisle")
public class AisleController extends BaseController {
    private String prefix = "rescue/aisle" ;

    @Autowired
    private IAisleService aisleService;
    @Autowired
    private IPointService pointService;
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IDijsktraService dijsktraService;
    @Autowired
    private IDictDataService dictDataService;

    @RequiresPermissions("rescue:route:view")
    @GetMapping()
    public String aisle() {
        return prefix + "/aisle" ;
    }

    /**
     * 查询通道列表
     */
    @RequiresPermissions("rescue:route:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Aisle aisle) {
        startPage();
        //查询比例
        DictData data = new DictData();
        data.setDictType("coefficient");
        DictData dictData = dictDataService.selectDictDataList(data).get(0);

        List<Aisle> list = aisleService.selectAisleList_(aisle);
        for(Aisle aisle1 : list){
            aisle1.setDistance((new BigDecimal(aisle1.getDistance()).multiply(new BigDecimal(Integer.valueOf(dictData.getDictValue()))).divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP)).doubleValue());
        }
        return getDataTable(list);
    }


    /**
     * 导出通道列表
     */
    @RequiresPermissions("rescue:aisle:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Aisle aisle) {
        List<Aisle> list = aisleService.selectAisleList(aisle);
        ExcelUtil<Aisle> util = new ExcelUtil<Aisle>(Aisle. class);
        return util.exportExcel(list, "aisle");
    }

    /**
     * 新增通道
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Point point = new Point();
        List<Point> pointList = pointService.selectPointList(point);
        mmap.put("pointList",pointList);
        Region region = new Region();
        region.setStatus("0");
        List<Region> regionList = regionService.selectRegionList(region);
        mmap.put("regionList",regionList);
        return prefix + "/add" ;
    }

    /**
     * 根据起点查询符合条件的目标点
     * @param pointId
     * @return
     */
    @PostMapping("/selectPoint")
    @ResponseBody
    public Map selectSystem(String pointId) {
        Map map = new HashMap();
        Dijsktra dij = new Dijsktra();
        dij.setPointId(pointId);
        Dijsktra dijsktra = dijsktraService.selectDijsktra(dij);
        String[] strs = dijsktra.getLarr().split(",");
        List<String> list = new ArrayList<>(Arrays.asList(strs));
        map.put("list",list);
        List<Dijsktra> dijsktraList = dijsktraService.selectList(map);
        map.put("dijsktraList",dijsktraList);
        return map;
    }


    /**
     * 新增保存通道
     */
    @RequiresPermissions("rescue:route:view")
    @Log(title = "通道" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Aisle aisle) {
        return toAjax(aisleService.insertAisle(aisle));
    }

    /**
     * 修改通道
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Aisle aisle =aisleService.selectAisleById(id);
        mmap.put("aisle" , aisle);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存通道
     */
    @RequiresPermissions("rescue:aisle:edit")
    @Log(title = "通道" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Aisle aisle) {
        return toAjax(aisleService.updateAisle(aisle));
    }

    /**
     * 删除通道
     */
    @RequiresPermissions("rescue:route:view")
    @Log(title = "通道" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aisleService.deleteAisleByIds(ids));
    }

    /**
     * 续签合同
     * @param
     * @param mmap
     * @return
     */
    @GetMapping("/change")
    public String renew(ModelMap mmap) {
        DictData data = new DictData();
        data.setDictType("coefficient");
        DictData dictData = dictDataService.selectDictDataList(data).get(0);
        mmap.put("dictData",dictData);
        return prefix + "/change" ;
    }

    /**
     * 续签合同 保存
     * @param dictData
     * @return
     */
    @PostMapping("/changeSave")
    @ResponseBody
    public AjaxResult changeSave(DictData dictData) {
        return toAjax(dictDataService.updateDictData(dictData));
    }

}
