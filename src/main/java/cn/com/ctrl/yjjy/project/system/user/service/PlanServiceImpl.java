package cn.com.ctrl.yjjy.project.system.user.service;
import java.util.List;

import cn.com.ctrl.yjjy.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.system.user.mapper.PlanMapper;
import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 预案 服务层实现
 *
 * @author zzmh
 * @date 2018-12-19
 */
@Data
@Log4j2
@Service
public class PlanServiceImpl implements IPlanService {
    @Autowired
    private PlanMapper planMapper;

    /**
     * 查询预案信息
     *
     * @param id 预案ID
     * @return 预案信息
     */
    @Override
    public Plan selectPlanById(String id) {
        return planMapper.selectPlanById(id);
    }

    /**
     * 查询预案列表
     *
     * @param plan 预案信息
     * @return 预案集合
     */
    @Override
    public List<Plan> selectPlanList(Plan plan) {
        return planMapper.selectPlanList(plan);
    }

    /**
     * 新增预案
     *
     * @param plan 预案信息
     * @return 结果
     */
    @Override
    public int insertPlan(Plan plan) {
        plan.setId(StringUtils.gainGUID());
        return planMapper.insertPlan(plan);
    }

    /**
     * 修改预案
     *
     * @param plan 预案信息
     * @return 结果
     */
    @Override
    public int updatePlan(Plan plan) {
        return planMapper.updatePlan(plan);
    }

    /**
     * 删除预案对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePlanByIds(String ids) {
        return planMapper.deletePlanByIds(Convert.toStrArray(ids));
    }
}