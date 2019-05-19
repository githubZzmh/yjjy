package cn.com.ctrl.yjjy.project.control.plan.service;
import java.util.List;

import cn.com.ctrl.yjjy.project.control.plan.domain.PlanListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.control.plan.mapper.PlanRoleMapper;
import cn.com.ctrl.yjjy.project.control.plan.domain.PlanRole;
import cn.com.ctrl.yjjy.common.support.Convert;
/**
 * 角色预案 服务层实现
 *
 * @author zzmh
 * @date 2018-12-25
 */
@Data
@Log4j2
@Service
public class PlanRoleServiceImpl implements IPlanRoleService {
    @Autowired
    private PlanRoleMapper planRoleMapper;
    /**
     * 查询角色预案信息
     *
     * @param id 角色预案ID
     * @return 角色预案信息
     */
    @Override
    public PlanRole selectPlanRoleById(String id) {
        return planRoleMapper.selectPlanRoleById(id);
    }
    /**
     * 查询角色预案信息
     *
     * @param planId 预案ID
     * @return 角色预案信息
     */
    @Override
    public List<PlanRole> selectPlanRoleListByPlanId(String planId) {
        return planRoleMapper.selectPlanRoleListByPlanId(planId);
    }
    /**
     * 查询角色预案列表
     *
     * @param plv 角色预案信息
     * @return 角色预案集合
     */
    @Override
    public List<PlanRole> selectPlanRoleList(PlanListView plv) {
        return planRoleMapper.selectPlanRoleList(plv);
    }
    /**
     * 新增角色预案
     *
     * @param planRole 角色预案信息
     * @return 结果
     */
    @Override
    public int insertPlanRole(PlanRole planRole) {
        return planRoleMapper.insertPlanRole(planRole);
    }
    /**
     * 修改角色预案
     *
     * @param planRole 角色预案信息
     * @return 结果
     */
    @Override
    public int updatePlanRole(PlanRole planRole) {
        return planRoleMapper.updatePlanRole(planRole);
    }
    /**
     * 删除角色预案对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePlanRoleByIds(String ids) {
        return planRoleMapper.deletePlanRoleByIds(Convert.toStrArray(ids));
    }
}