package cn.com.ctrl.yjjy.project.system.user.service;
import java.util.List;
import cn.com.ctrl.yjjy.project.system.user.entity.EchartsSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.system.user.mapper.PlanMapMapper;
import cn.com.ctrl.yjjy.project.system.user.domain.PlanMap;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 预案事故地图路线及播报 服务层实现
 *
 * @author zzmh
 * @date 2018-12-27
 */
@Data
@Log4j2
@Service
public class PlanMapServiceImpl implements IPlanMapService {
    @Autowired
    private PlanMapMapper planMapMapper;

    /**
     * 查询预案事故地图路线及播报信息
     *
     * @param id 预案事故地图路线及播报ID
     * @return 预案事故地图路线及播报信息
     */
    @Override
    public List<EchartsSimple> selectPlanMapById(String id) {
        return planMapMapper.selectPlanMapById(id);
    }

    /**
     * 查询预案事故地图路线及播报列表
     *
     * @param planMap 预案事故地图路线及播报信息
     * @return 预案事故地图路线及播报集合
     */
    @Override
    public List<EchartsSimple> selectPlanMapList(PlanMap planMap) {
        return planMapMapper.selectPlanMapList(planMap);
    }

    /**
     * 新增预案事故地图路线及播报
     *
     * @param planMap 预案事故地图路线及播报信息
     * @return 结果
     */
    @Override
    public int insertPlanMap(PlanMap planMap) {
        return planMapMapper.insertPlanMap(planMap);
    }

    /**
     * 修改预案事故地图路线及播报
     *
     * @param planMap 预案事故地图路线及播报信息
     * @return 结果
     */
    @Override
    public int updatePlanMap(PlanMap planMap) {
        return planMapMapper.updatePlanMap(planMap);
    }

    /**
     * 删除预案事故地图路线及播报对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePlanMapByIds(String ids) {
        return planMapMapper.deletePlanMapByIds(Convert.toStrArray(ids));
    }
}