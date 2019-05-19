package cn.com.ctrl.yjjy.project.control.plan.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
/**
 * 角色预案view
 *
 * @author zzmh
 * @date 2018-12-25
 */
@Data
public class PlanListView extends BaseEntity {
    private static final long serialVersionUID=1L;
    /**  */
    private String id;
    /** 预案id */
    private String planId;
    /** 角色id */
    private String roleId;
    /** 预案名称 */
    private String planName;
    /** 角色名称 */
    private String roleName;
    /** 预案应对名称 */
    private String name;
    /** 预案文件url */
    private String url;
}