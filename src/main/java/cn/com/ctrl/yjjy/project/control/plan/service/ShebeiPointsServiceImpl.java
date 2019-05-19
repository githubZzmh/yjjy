package cn.com.ctrl.yjjy.project.control.plan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.control.plan.mapper.ShebeiPointsMapper;
import cn.com.ctrl.yjjy.project.control.plan.domain.ShebeiPoints;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 设备标点地图 服务层实现
 *
 * @author yjjy
 * @date 2019-01-07
 */
@Data
@Log4j2
@Service
public class ShebeiPointsServiceImpl implements IShebeiPointsService {
    @Autowired
    private ShebeiPointsMapper shebeiPointsMapper;

    /**
     * 查询设备标点地图信息
     *
     * @param sPoints 设备标点地图ID
     * @return 设备标点地图信息
     */
    @Override
    public ShebeiPoints selectShebeiPointsById(String sPoints) {
        return shebeiPointsMapper.selectShebeiPointsById(sPoints);
    }

    /**
     * 查询设备标点地图列表
     *
     * @param shebeiPoints 设备标点地图信息
     * @return 设备标点地图集合
     */
    @Override
    public List<ShebeiPoints> selectShebeiPointsList(ShebeiPoints shebeiPoints) {
        return shebeiPointsMapper.selectShebeiPointsList(shebeiPoints);
    }

    /**
     * 新增设备标点地图
     *
     * @param shebeiPoints 设备标点地图信息
     * @return 结果
     */
    @Override
    public int insertShebeiPoints(ShebeiPoints shebeiPoints) {
        return shebeiPointsMapper.insertShebeiPoints(shebeiPoints);
    }

    /**
     * 修改设备标点地图
     *
     * @param shebeiPoints 设备标点地图信息
     * @return 结果
     */
    @Override
    public int updateShebeiPoints(ShebeiPoints shebeiPoints) {
        return shebeiPointsMapper.updateShebeiPoints(shebeiPoints);
    }

    /**
     * 删除设备标点地图对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteShebeiPointsByIds(String ids) {
        return shebeiPointsMapper.deleteShebeiPointsByIds(Convert.toStrArray(ids));
    }

}
