package cn.com.ctrl.yjjy.project.rescue.aisle.service;
import java.math.BigDecimal;
import java.util.*;


import cn.com.ctrl.yjjy.common.utils.StringUtils;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.project.control.plan.mapper.PointMapper;
import cn.com.ctrl.yjjy.project.rescue.aisle.domain.Aisle;
import cn.com.ctrl.yjjy.project.rescue.aisle.mapper.AisleMapper;
import cn.com.ctrl.yjjy.project.system.dict.domain.DictData;
import cn.com.ctrl.yjjy.project.system.dict.mapper.DictDataMapper;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.project.tool.map.mapper.DijsktraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通道 服务层实现
 *
 * @author zzmh
 * @date 2018-12-18
 */
@Data
@Log4j2
@Service
@Transactional
public class AisleServiceImpl implements IAisleService {
    @Autowired
    private AisleMapper aisleMapper;
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private DijsktraMapper dijsktraMapper;
    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 查询通道信息
     *
     * @param id 通道ID
     * @return 通道信息
     */
    @Override
    public Aisle selectAisleById(String id) {
        return aisleMapper.selectAisleById(id);
    }
    /**
     * 查询通道列表
     *
     * @param aisle 通道信息
     * @return 通道集合
     */
    @Override
    public List<Aisle> selectAisleList(Aisle aisle) {
        return aisleMapper.selectAisleList(aisle);
    }
    /**
     * 查询通道列表
     *
     * @param aisle 通道信息
     * @return 通道集合
     */
    @Override
    public List<Aisle> selectAisleList_(Aisle aisle) {
        return aisleMapper.selectAisleList_(aisle);
    }
    /**
     * 新增通道
     *
     * @param aisle 通道信息
     * @return 结果
     */
    @Override
    public int insertAisle(Aisle aisle) {
        String groupId = StringUtils.gainGUID();
        //添加路径
        aisle.setId(StringUtils.gainGUID());

        //查询比例
        DictData data = new DictData();
        data.setDictType("coefficient");
        DictData dictData = dictDataMapper.selectDictDataList(data).get(0);
        aisle.setDistance((new BigDecimal(aisle.getDistance()).divide(new BigDecimal(dictData.getDictValue())).multiply(new BigDecimal(100)).setScale(6, BigDecimal.ROUND_HALF_UP)).doubleValue());

        Point beginPoint = pointMapper.selectPointById(aisle.getBeginPoint());
        aisle.setBeginPointX(beginPoint.getPointX());
        aisle.setBeginPointY(beginPoint.getPointY());
        Point endPoint = pointMapper.selectPointById(aisle.getEndPoint());
        aisle.setEndPointX(endPoint.getPointX());
        aisle.setEndPointY(endPoint.getPointY());
        aisle.setGroupId(groupId);
        Dijsktra dijsktra = new Dijsktra();
        dijsktra.setPointId(beginPoint.getId());
        Dijsktra dijsktra2 = dijsktraMapper.selectDijsktra(dijsktra);
        dijsktra.setPointId(endPoint.getId());
        Dijsktra dijsktra3 = dijsktraMapper.selectDijsktra(dijsktra);
        aisle.setName(dijsktra2.getWeightNum()+"至"+dijsktra3.getWeightNum());
        int i = aisleMapper.insertAisle(aisle);
        //返回路径
        aisle.setId(StringUtils.gainGUID());
        aisle.setBeginPointX(endPoint.getPointX());
        aisle.setBeginPointY(endPoint.getPointY());
        aisle.setEndPointX(beginPoint.getPointX());
        aisle.setEndPointY(beginPoint.getPointY());
        aisle.setName(dijsktra3.getWeightNum()+"至"+dijsktra2.getWeightNum());
        int j = aisleMapper.insertAisle(aisle);
        //重组两个点的larr
        String str2 = checkSort(dijsktra2.getLarr(),Integer.valueOf(dijsktra3.getWeightNum()));
        dijsktra2.setLarr(str2.substring(0,str2.length()-1));
        dijsktraMapper.updateDijsktra(dijsktra2);

        String str3 = checkSort(dijsktra3.getLarr(),Integer.valueOf(dijsktra2.getWeightNum()));
        dijsktra3.setLarr(str3.substring(0,str3.length()-1));
        dijsktraMapper.updateDijsktra(dijsktra3);

        //重组和两个点的alarr相同的所有点的alarr
//        Map map = new HashMap();
//        map.put("larr",dijsktra2.getLarr());
//        List<Dijsktra> dkList = dijsktraMapper.selectList(map);
//        map.put("larr",dijsktra3.getLarr());
//        List<Dijsktra> dkList2 = dijsktraMapper.selectList(map);
        String str = mergeSort(dijsktra2.getAlarr(),dijsktra3.getAlarr());
        List<Dijsktra> dijsktraList = new ArrayList<>();
        String[] arr = str.substring(0,str.length()-1).split(",");
//        dkList.addAll(dkList2);
        for(int n = 0;n<arr.length;n++){
            Dijsktra d = dijsktraMapper.selectDijsktraByWeightNumList(arr[n]);
            d.setAlarr(str.substring(0,str.length()-1));
            dijsktraMapper.updateDijsktra(d);
        }

        //根据重组后的alarr查询 去一个起点
        Dijsktra dijsktra1 = new Dijsktra();
        dijsktra1.setAlarr(str.substring(0,str.length()-1));
        dijsktra1.setOpstatus("1");
        chongzu(dijsktra1,dijsktra2);

        return i;
    }

    public String mergeSort(String alarr1,String alarr2){
        String str = "";
        String[] str1 = alarr1.split(",");
        String[] str2 = alarr2.split(",");
        Set list = new HashSet(Arrays.asList(str1));
        list.addAll(Arrays.asList(str2));
        String[] str3 = new String[list.size()];
        list.toArray(str3);
        int[] array = new int[str3.length];
        for(int x=0;x<str3.length;x++){
            array[x]=Integer.parseInt(str3[x]);
        }
        Arrays.sort(array);
        for(int i=0;i<array.length;i++){
            str += array[i]+",";
        }
        return str;
    }


    //每组留一个原点
    public void chongzu(Dijsktra dijsktra1,Dijsktra dijsktra2){
        List<Dijsktra> dijsktraList2 = dijsktraMapper.selectDijsktraList(dijsktra1);
        if(dijsktraList2.size() == 0){//这个组没有原点
            String weightNum = dijsktra2.getAlarr().split(",")[0];
            Dijsktra dij = new Dijsktra();
            dij.setWeightNum(weightNum);
            dij = dijsktraMapper.selectDijsktra(dij);
            dij.setOpstatus("1");
            dijsktraMapper.updateDijsktra(dij);
        }else if(dijsktraList2.size() == 1){

        }else{
            for(Dijsktra d : dijsktraList2){
                d.setOpstatus("0");
                dijsktraMapper.updateDijsktra(d);
            }
            Dijsktra dij = dijsktraList2.get(0);
            dij.setOpstatus("1");
            dijsktraMapper.updateDijsktra(dij);
        }
    }

    //重组larr,
    public String checkSort(String param,int weightNum){
        String str = "";
        String[] strs = param.split(",");
        int[] array = new int[strs.length + 1];
        array[array.length-1] = weightNum;
        for(int i=0;i<strs.length;i++){
            array[i]=Integer.parseInt(strs[i]);
        }
        Arrays.sort(array);
        for(int i=0;i<array.length;i++){
            str += array[i]+",";
        }
        return str;
    }

    /**
     * 修改通道
     *
     * @param aisle 通道信息
     * @return 结果
     */
    @Override
    public int updateAisle(Aisle aisle) {
        return aisleMapper.updateAisle(aisle);
    }

    /**
     * 删除通道对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAisleByIds(String ids) {
        //删除往返路径
        Aisle ais = aisleMapper.selectAisleById(ids);
        //处理dijsktra
//        Aisle aisle = new Aisle();
//        aisle.setGroupId(ais.getGroupId());
//        List<Aisle> aisles = aisleMapper.selectAisleList_(aisle);
        Point p = new Point();
        p.setPointX(ais.getBeginPointX());
        p.setPointY(ais.getBeginPointY());
        Point point = pointMapper.selectPointList(p).get(0);
        Dijsktra dij = new Dijsktra();
        dij.setPointId(point.getId());
        Dijsktra dijsktra1 = dijsktraMapper.selectDijsktra(dij);
        p.setPointX(ais.getEndPointX());
        p.setPointY(ais.getEndPointY());
        Point point2 = pointMapper.selectPointList(p).get(0);
        dij.setPointId(point2.getId());
        Dijsktra dijsktra2 = dijsktraMapper.selectDijsktra(dij);
        //修改larr
        dijsktra1.setLarr(deleteChongzu(dijsktra1,dijsktra2));
        dijsktraMapper.updateDijsktra(dijsktra1);
        dijsktra2.setLarr(deleteChongzu(dijsktra2,dijsktra1));
        dijsktraMapper.updateDijsktra(dijsktra2);
        //重组alarr
        String s = deleteChongzu(dijsktra1,dijsktra2);
        updateAlarr(s);
        s = deleteChongzu(dijsktra2,dijsktra1);
        updateAlarr(s);

        //设置原点
        Dijsktra dijs = new Dijsktra();
        dijs.setPointId(point.getId());
        String alarr = dijsktraMapper.selectDijsktra(dijs).getAlarr();
        Dijsktra dijsktra = dijsktraMapper.selectDijsktraByWeightNumList(alarr.split(",")[0]);
        dijsktra.setOpstatus("1");
        dijsktraMapper.updateDijsktra(dijsktra);
        dijs.setPointId(point2.getId());
        String alarr2 = dijsktraMapper.selectDijsktra(dijs).getAlarr();
        dijsktra = dijsktraMapper.selectDijsktraByWeightNumList(alarr2.split(",")[0]);
        dijsktra.setOpstatus("1");
        dijsktraMapper.updateDijsktra(dijsktra);


        return aisleMapper.deleteAisleByGroupId(ais.getGroupId());
    }

    public void updateAlarr(String s){
        String str = resetAlarr(s);
        Dijsktra dijsktra = new Dijsktra();
        dijsktra.setLarr(str);
        List<Dijsktra> dijsktraList = dijsktraMapper.selectDijsktraByLarr(dijsktra);
        for(Dijsktra d : dijsktraList){
            d.setAlarr(str);
            dijsktraMapper.updateDijsktra(d);
        }
    }

    //重组alarr
    public String resetAlarr(String s){
        Dijsktra dijsktra = new Dijsktra();
        dijsktra.setLarr(s);
        List<Dijsktra> dijsktraList = dijsktraMapper.selectDijsktraByLarr(dijsktra);
        Set<String> set = new HashSet<>();
        for(Dijsktra d : dijsktraList){
            String[] str = d.getLarr().split(",");
            for(int i=0; i<str.length;i++){
                set.add(str[i]);
            }
        }
        String[] arr = new String[set.size()];
        set.toArray(arr);
        Arrays.sort(arr);
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+",";
        }
        if(!s.equals(str.substring(0,str.length()-1))){
            return resetAlarr(str.substring(0,str.length()-1));
        }
        return str.substring(0,str.length()-1);
    }

    //删除重组larr
    public String deleteChongzu(Dijsktra dijsktra1,Dijsktra dijsktra2){
        //修改larr
        String[] arr1 = dijsktra1.getLarr().split(",");
        List<String> list1 = new ArrayList<>();
        for(int i=0;i<arr1.length;i++){
            if(dijsktra2.getWeightNum().equals(arr1[i])){

            }else{
                list1.add(arr1[i]);
            }
        }
        String[] strings = new String[list1.size()];
        list1.toArray(strings);
        String str = "";
        for(int i=0;i<strings.length;i++){
            str += strings[i] + ",";
        }

        return str.substring(0,str.length()-1);
    }


    /**
     * 批量占用通道
     *
     * @param ids 需要占用的数据ID
     * @return 结果
     */
    @Override
    public List<Aisle> selectAisleByIds(String ids) {
        return aisleMapper.selectAisleByIds(Convert.toStrArray(ids));
    }
    /**
     * 计算完成
     *
     * @return 结果
     */
    @Override
    public int uverAisle() {
        return aisleMapper.uverAisle();
    }



}