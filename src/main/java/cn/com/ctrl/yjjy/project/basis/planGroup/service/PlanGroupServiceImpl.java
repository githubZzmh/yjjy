package cn.com.ctrl.yjjy.project.basis.planGroup.service;

import java.util.List;

import cn.com.ctrl.yjjy.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.basis.planGroup.mapper.PlanGroupMapper;
import cn.com.ctrl.yjjy.project.basis.planGroup.domain.PlanGroup;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 预案分组 服务层实现
 *
 * @author yjjy
 * @date 2019-04-16
 */
@Data
@Log4j2
@Service
public class PlanGroupServiceImpl implements IPlanGroupService {
    @Autowired
    private PlanGroupMapper planGroupMapper;

    /**
     * 查询预案分组信息
     *
     * @param id 预案分组ID
     * @return 预案分组信息
     */
    @Override
    public PlanGroup selectPlanGroupById(String id) {
        return planGroupMapper.selectPlanGroupById(id);
    }

    /**
     * 查询预案分组列表
     *
     * @param planGroup 预案分组信息
     * @return 预案分组集合
     */
    @Override
    public List<PlanGroup> selectPlanGroupList(PlanGroup planGroup) {
        return planGroupMapper.selectPlanGroupList(planGroup);
    }

    /**
     * 新增预案分组
     *
     * @param planGroup 预案分组信息
     * @return 结果
     */
    @Override
    public int insertPlanGroup(PlanGroup planGroup) {
        planGroup.setId(StringUtils.gainGUID());
        return planGroupMapper.insertPlanGroup(planGroup);
    }

    /**
     * 修改预案分组
     *
     * @param planGroup 预案分组信息
     * @return 结果
     */
    @Override
    public int updatePlanGroup(PlanGroup planGroup) {
        return planGroupMapper.updatePlanGroup(planGroup);
    }

    /**
     * 删除预案分组对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePlanGroupByIds(String ids) {
        return planGroupMapper.deletePlanGroupByIds(Convert.toStrArray(ids));
    }

}
