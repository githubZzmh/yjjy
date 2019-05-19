package cn.com.ctrl.yjjy.project.system.user.service;

import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import java.util.List;

/**
 * 预案 服务层
 *
 * @author zzmh
 * @date 2018-12-19
 */
public interface IPlanService {
    /**
     * 查询预案信息
     *
     * @param id 预案ID
     * @return 预案信息
     */
    Plan selectPlanById(String id);

    /**
     * 查询预案列表
     *
     * @param plan 预案信息
     * @return 预案集合
     */
    List<Plan> selectPlanList(Plan plan);

    /**
     * 新增预案
     *
     * @param plan 预案信息
     * @return 结果
     */
    int insertPlan(Plan plan);

    /**
     * 修改预案
     *
     * @param plan 预案信息
     * @return 结果
     */
    int updatePlan(Plan plan);

    /**
     * 删除预案信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlanByIds(String ids);

}
