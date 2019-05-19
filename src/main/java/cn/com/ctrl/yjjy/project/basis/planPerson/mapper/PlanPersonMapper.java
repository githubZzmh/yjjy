package cn.com.ctrl.yjjy.project.basis.planPerson.mapper;

import cn.com.ctrl.yjjy.project.basis.planPerson.domain.PlanPerson;
import java.util.List;

/**
 * 预案分组人员 数据层
 *
 * @author yjjy
 * @date 2019-04-16
 */
public interface PlanPersonMapper {
    /**
     * 查询预案分组人员信息
     *
     * @param id 预案分组人员ID
     * @return 预案分组人员信息
     */
        PlanPerson selectPlanPersonById(String id);

    /**
     * 查询预案分组人员列表
     *
     * @param planPerson 预案分组人员信息
     * @return 预案分组人员集合
     */
    List<PlanPerson> selectPlanPersonList(PlanPerson planPerson);

    /**
     * 新增预案分组人员
     *
     * @param planPerson 预案分组人员信息
     * @return 结果
     */
    int insertPlanPerson(PlanPerson planPerson);

    /**
     * 修改预案分组人员
     *
     * @param planPerson 预案分组人员信息
     * @return 结果
     */
    int updatePlanPerson(PlanPerson planPerson);

    /**
     * 删除预案分组人员
     *
     * @param id 预案分组人员ID
     * @return 结果
     */
    int deletePlanPersonById(String id);

    /**
     * 批量删除预案分组人员
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlanPersonByIds(String[] ids);

    public int batchUserRole(List<PlanPerson> planPersonList);

}