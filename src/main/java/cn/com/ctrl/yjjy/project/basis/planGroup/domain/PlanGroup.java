package cn.com.ctrl.yjjy.project.basis.planGroup.domain;

import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 预案分组表 p_plan_group
 *
 * @author yjjy
 * @date 2019-04-16
 */
@Data
public class PlanGroup extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 预案id */
    private String planId;
/** 预案名称 */
    private String name;
/** 领导短信内容 */
    private String leaderMsg;
/** 员工短信内容 */
    private String workerMsg;

    private String planName;
    private String word;
}