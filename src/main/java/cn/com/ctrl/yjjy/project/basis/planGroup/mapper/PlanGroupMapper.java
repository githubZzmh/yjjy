package cn.com.ctrl.yjjy.project.basis.planGroup.mapper;

import cn.com.ctrl.yjjy.project.basis.planGroup.domain.PlanGroup;
import java.util.List;

/**
 * 预案分组 数据层
 *
 * @author yjjy
 * @date 2019-04-16
 */
public interface PlanGroupMapper {
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
     * 删除预案分组
     *
     * @param id 预案分组ID
     * @return 结果
     */
    int deletePlanGroupById(String id);

    /**
     * 批量删除预案分组
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlanGroupByIds(String[] ids);

}