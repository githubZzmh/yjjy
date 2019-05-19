package cn.com.ctrl.yjjy.project.control.plan.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import cn.com.ctrl.yjjy.project.system.role.domain.Role;
import cn.com.ctrl.yjjy.project.system.user.domain.Plan;
import lombok.Data;
/**
 * 角色预案表 p_plan_role
 *
 * @author zzmh
 * @date 2018-12-25
 */
@Data
public class PlanRole extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 预案id */
    private String planId;
/** 角色id */
    private String roleId;
/** 预案应对名称 */
    private String name;
/** 预案文件url */
    private String url;
    private Plan plan;
    private Role role;
}