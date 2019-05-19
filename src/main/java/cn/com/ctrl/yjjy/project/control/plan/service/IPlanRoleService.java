package cn.com.ctrl.yjjy.project.control.plan.service;
import cn.com.ctrl.yjjy.project.control.plan.domain.PlanListView;
import cn.com.ctrl.yjjy.project.control.plan.domain.PlanRole;
import java.util.List;
/**
 * 角色预案 服务层
 *
 * @author zzmh
 * @date 2018-12-25
 */
public interface IPlanRoleService {
    /**
     * 查询角色预案信息
     *
     * @param id 角色预案ID
     * @return 角色预案信息
     */
    PlanRole selectPlanRoleById(String id);
    /**
     * 查询角色预案信息
     *
     * @param planId 预案ID
     * @return 角色预案信息
     */
    List<PlanRole> selectPlanRoleListByPlanId(String planId);
    /**
     * 查询角色预案列表
     *
     * @param plv 角色预案信息
     * @return 角色预案集合
     */
    List<PlanRole> selectPlanRoleList(PlanListView plv);
    /**
     * 新增角色预案
     *
     * @param planRole 角色预案信息
     * @return 结果
     */
    int insertPlanRole(PlanRole planRole);
    /**
     * 修改角色预案
     *
     * @param planRole 角色预案信息
     * @return 结果
     */
    int updatePlanRole(PlanRole planRole);
    /**
     * 删除角色预案信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePlanRoleByIds(String ids);
}