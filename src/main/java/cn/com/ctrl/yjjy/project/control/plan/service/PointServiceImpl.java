package cn.com.ctrl.yjjy.project.control.plan.service;
import java.util.List;

import cn.com.ctrl.yjjy.common.utils.StringUtils;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.mapper.ShebeiMapper;
import cn.com.ctrl.yjjy.project.tool.map.domain.Dijsktra;
import cn.com.ctrl.yjjy.project.tool.map.mapper.DijsktraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.control.plan.mapper.PointMapper;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import cn.com.ctrl.yjjy.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 百度地图点 服务层实现
 *
 * @author zzmh
 * @date 2019-01-05
 */
@Data
@Log4j2
@Service
@Transactional
public class PointServiceImpl implements IPointService {
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private ShebeiMapper shebeiMapper;
    @Autowired
    private DijsktraMapper dijsktraMapper;
    /**
     * 查询百度地图点信息
     *
     * @param id 百度地图点ID
     * @return 百度地图点信息
     */
    @Override
    public Point selectPointById(String id) {
        return pointMapper.selectPointById(id);
    }
    /**
     * 查询百度地图点列表
     *
     * @param point 百度地图点信息
     * @return 百度地图点集合
     */
    @Override
    public List<Point> selectPointList(Point point) {
        return pointMapper.selectPointList(point);
    }
    /**
     * 新增百度地图点
     *
     * @param point 百度地图点信息
     * @return 结果
     */
    @Override
    public int insertPoint(Point point) {
        if("1".equals(point.getType())){
            Shebei shebei = shebeiMapper.selectShebeiById(point.getId());
            point.setPointX(shebei.getPointX());
            point.setPointY(shebei.getPointY());
        } else {
            point.setId(StringUtils.gainGUID());
        }

        Dijsktra dijsktra = new Dijsktra();
        //取weight_num最大值加1
        String weightNum = dijsktraMapper.selectMaxWeightNum();
        if(null == weightNum || "".equals(weightNum)){
            dijsktra.setWeightNum("1");
        }else{
            dijsktra.setWeightNum(String.valueOf(Integer.valueOf(weightNum)+1));
        }
        dijsktra.setId(StringUtils.gainGUID());
        dijsktra.setPointId(point.getId());
        dijsktra.setOpstatus("1");
        dijsktra.setLarr(dijsktra.getWeightNum());
        dijsktra.setAlarr(dijsktra.getWeightNum());
        dijsktra.setPointName(dijsktra.getWeightNum()+"号标点");
        dijsktraMapper.insertDijsktra(dijsktra);
        return pointMapper.insertPoint(point);
    }
    /**
     * 修改百度地图点
     *
     * @param point 百度地图点信息
     * @return 结果
     */
    @Override
    public int updatePoint(Point point) {
        return pointMapper.updatePoint(point);
    }
    /**
     * 删除百度地图点对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePointByIds(String ids) {
        Point point = pointMapper.selectPointById(ids);
        Dijsktra d = new Dijsktra();
        d.setPointId(point.getId());
        Dijsktra dijsktra = dijsktraMapper.selectDijsktra(d);
        if(dijsktra.getLarr().split(",").length>1){
            return 0;
        }else{
            dijsktraMapper.deleteDijsktraById(dijsktra.getId());
            return pointMapper.deletePointByIds(Convert.toStrArray(ids));
        }
    }
}