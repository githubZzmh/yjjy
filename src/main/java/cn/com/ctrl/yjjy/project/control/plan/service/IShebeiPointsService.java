package cn.com.ctrl.yjjy.project.control.plan.service;

import cn.com.ctrl.yjjy.project.control.plan.domain.ShebeiPoints;
import java.util.List;

/**
 * 设备标点地图 服务层
 *
 * @author zzmh
 * @date 2019-01-07
 */
public interface IShebeiPointsService {
    /**
     * 查询设备标点地图信息
     *
     * @param sPoints 设备标点地图ID
     * @return 设备标点地图信息
     */
    ShebeiPoints selectShebeiPointsById(String sPoints);

    /**
     * 查询设备标点地图列表
     *
     * @param shebeiPoints 设备标点地图信息
     * @return 设备标点地图集合
     */
    List<ShebeiPoints> selectShebeiPointsList(ShebeiPoints shebeiPoints);

    /**
     * 新增设备标点地图
     *
     * @param shebeiPoints 设备标点地图信息
     * @return 结果
     */
    int insertShebeiPoints(ShebeiPoints shebeiPoints);

    /**
     * 修改设备标点地图
     *
     * @param shebeiPoints 设备标点地图信息
     * @return 结果
     */
    int updateShebeiPoints(ShebeiPoints shebeiPoints);

    /**
     * 删除设备标点地图信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteShebeiPointsByIds(String ids);

}
