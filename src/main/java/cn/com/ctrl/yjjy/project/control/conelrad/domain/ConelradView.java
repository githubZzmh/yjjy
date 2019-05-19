package cn.com.ctrl.yjjy.project.control.conelrad.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
/**
 * 紧急广播
 *
 * @author zzmh
 * @date 2018-12-25
 */
@Data
public class ConelradView extends BaseEntity {
    private static final long serialVersionUID=1L;
    /** id */
    private String id;
    /** 主机号码 */
    private String numId;
    /** 所属分组 */
    private String name;
    /** 主机IP */
    private String ip;
    /** 设备横坐标 */
    private String pointX;
    /** 设备纵坐标 */
    private String pointY;
}