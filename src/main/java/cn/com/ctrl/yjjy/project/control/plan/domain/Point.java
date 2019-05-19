package cn.com.ctrl.yjjy.project.control.plan.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;
/**
 * 标点表 p_point
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
public class Point extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 标点名称 */
    private String name;
/** 标点编号 */
    private String numId;
/** 标点横坐标 */
    private Double pointX;
/** 标点纵坐标 */
    private Double pointY;
    /**
     * 标点类型
     */
    private String type;


/**辅助指端------------------------------------------------------*/
    /** 设备 */
    private String shebeiId;


}