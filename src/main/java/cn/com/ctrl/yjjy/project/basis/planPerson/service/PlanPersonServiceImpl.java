package cn.com.ctrl.yjjy.project.basis.planPerson.service;

import java.util.ArrayList;
import java.util.List;

import cn.com.ctrl.yjjy.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.basis.planPerson.mapper.PlanPersonMapper;
import cn.com.ctrl.yjjy.project.basis.planPerson.domain.PlanPerson;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 预案分组人员 服务层实现
 *
 * @author yjjy
 * @date 2019-04-16
 */
@Data
@Log4j2
@Service
public class PlanPersonServiceImpl implements IPlanPersonService {
    @Autowired
    private PlanPersonMapper planPersonMapper;

    /**
     * 查询预案分组人员信息
     *
     * @param id 预案分组人员ID
     * @return 预案分组人员信息
     */
    @Override
    public PlanPerson selectPlanPersonById(String id) {
        return planPersonMapper.selectPlanPersonById(id);
    }

    /**
     * 查询预案分组人员列表
     *
     * @param planPerson 预案分组人员信息
     * @return 预案分组人员集合
     */
    @Override
    public List<PlanPerson> selectPlanPersonList(PlanPerson planPerson) {
        return planPersonMapper.selectPlanPersonList(planPerson);
    }

    /**
     * 新增预案分组人员
     *
     * @param planPerson 预案分组人员信息
     * @return 结果
     */
    @Override
    public int insertPlanPerson(PlanPerson planPerson) {
        int i = 0;
        String[] userIds = planPerson.getUserIds();
        if (StringUtils.isNotNull(userIds)) {
//            List<PlanPerson> list = new ArrayList<PlanPerson>();
            for (String userId : planPerson.getUserIds()) {
                planPerson.setId(StringUtils.gainGUID());
                planPerson.setUserId(userId);
                i = planPersonMapper.insertPlanPerson(planPerson);
            }
        }
        return i;
    }

    /**
     * 修改预案分组人员
     *
     * @param planPerson 预案分组人员信息
     * @return 结果
     */
    @Override
    public int updatePlanPerson(PlanPerson planPerson) {
        return planPersonMapper.updatePlanPerson(planPerson);
    }

    /**
     * 删除预案分组人员对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePlanPersonByIds(String ids) {
        return planPersonMapper.deletePlanPersonByIds(Convert.toStrArray(ids));
    }

}
