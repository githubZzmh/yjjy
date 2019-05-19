package cn.com.ctrl.yjjy.project.system.user.mapper;
import cn.com.ctrl.yjjy.project.system.user.domain.PlanMap;
import cn.com.ctrl.yjjy.project.system.user.entity.EchartsSimple;
import java.util.List;
/**
 * 预案事故地图路线及播报 数据层
 *
 * @author zzmh
 * @date 2018-12-27
 */
public interface PlanMapMapper {
    /**
     * 查询预案事故地图路线及播报信息
     *
     * @param id 预案事故地图路线及播报ID
     * @return 预案事故地图路线及播报信息
     */
    List<EchartsSimple> selectPlanMapById(String id);

    /**
     * 查询预案事故地图路线及播报列表
     *
     * @param planMap 预案事故地图路线及播报信息
     * @return 预案事故地图路线及播报集合
     */
    List<EchartsSimple> selectPlanMapList(PlanMap planMap);

    /**
     * 新增预案事故地图路线及播报
     *
     * @param planMap 预案事故地图路线及播报信息
     * @return 结果
     */
    int insertPlanMap(PlanMap planMap);

    /**
     * 修改预案事故地图路线及播报
     *
     * @param planMap 预案事故地图路线及播报信息
     * @return 结果
     */
    int updatePlanMap(PlanMap planMap);

    /**
     * 删除预案事故地图路线及播报
     *
     * @param id 预案事故地图路线及播报ID
     * @return 结果
     */
    int deletePlanMapById(String id);
    /**
     * 批量删除预案事故地图路线及播报
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlanMapByIds(String[] ids);
}