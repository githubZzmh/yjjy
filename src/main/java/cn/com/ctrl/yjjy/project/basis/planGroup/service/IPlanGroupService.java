package cn.com.ctrl.yjjy.project.basis.planGroup.service;

import cn.com.ctrl.yjjy.project.basis.planGroup.domain.PlanGroup;
import java.util.List;

/**
 * 预案分组 服务层
 *
 * @author yjjy
 * @date 2019-04-16
 */
public interface IPlanGroupService {
    /**
     * 查询预案分组信息
     *
     * @param id 预案分组ID
     * @return 预案分组信息
     */
        PlanGroup selectPlanGroupById(String id);

    /**
     * 查询预案分组列表
     *
     * @param planGroup 预案分组信息
     * @return 预案分组集合
     */
    List<PlanGroup> selectPlanGroupList(PlanGroup planGroup);

    /**
     * 新增预案分组
     *
     * @param planGroup 预案分组信息
     * @return 结果
     */
    int insertPlanGroup(PlanGroup planGroup);

    /**
     * 修改预案分组
     *
     * @param planGroup 预案分组信息
     * @return 结果
     */
    int updatePlanGroup(PlanGroup planGroup);

    /**
     * 删除预案分组信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlanGroupByIds(String ids);

}
