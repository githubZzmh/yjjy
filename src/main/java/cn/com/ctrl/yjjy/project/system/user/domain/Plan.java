package cn.com.ctrl.yjjy.project.system.user.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 预案表 p_plan
 *
 * @author zzmh
 * @date 2018-12-19
 */
@Data
public class Plan extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 预案名称 */
    private String name;
}