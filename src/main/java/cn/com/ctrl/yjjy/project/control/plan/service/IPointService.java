package cn.com.ctrl.yjjy.project.control.plan.service;
import cn.com.ctrl.yjjy.project.control.plan.domain.Point;
import java.util.List;
/**
 * 百度地图点 服务层
 *
 * @author zzmh
 * @date 2019-01-05
 */
public interface IPointService {
    /**
     * 查询百度地图点信息
     *
     * @param id 百度地图点ID
     * @return 百度地图点信息
     */
    Point selectPointById(String id);
    /**
     * 查询百度地图点列表
     *
     * @param point 百度地图点信息
     * @return 百度地图点集合
     */
    List<Point> selectPointList(Point point);
    /**
     * 新增百度地图点
     *
     * @param point 百度地图点信息
     * @return 结果
     */
    int insertPoint(Point point);
    /**
     * 修改百度地图点
     *
     * @param point 百度地图点信息
     * @return 结果
     */
    int updatePoint(Point point);
    /**
     * 删除百度地图点信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePointByIds(String ids);
}