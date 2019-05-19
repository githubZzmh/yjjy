package cn.com.ctrl.yjjy.project.basis.planPerson.domain;

import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 预案分组人员表 p_plan_person
 *
 * @author yjjy
 * @date 2019-04-16
 */
@Data
public class PlanPerson extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 预案分组id */
    private String groupId;
/** 人员id */
    private String userId;
/** 0 领导 1 普通员工 */
    private String isLeader;
/** 人员名称 */
    private String name;
/** 电话 */
    private String mobile;
    private String[] userIds;
    private String groupName;
    private String userName;
}