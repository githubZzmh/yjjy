package cn.com.ctrl.yjjy.project.rescue.aisle.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 通道表 p_aisle
 *
 * @author zzmh
 * @date 2018-12-18
 */
@Data
public class Aisle extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/**  */
    private String groupId;
/** 通道名称 */
    private String name;
    /** 通道名称 */
    private String kname;
/** 状态0:可用1:不可用 */
    private String status;
/** 起始点横坐标 */
    private Double beginPointX;
/** 起始点纵坐标 */
    private Double beginPointY;
/** 目标点横坐标 */
    private Double endPointX;
/** 目标点纵坐标 */
    private Double endPointY;
/** 前进方向 */
    private String direction;
/** 前进距离 */
    private Double distance;
/** 区域id */
    private String regionId;


    /** ----------------------------------------------------- */
    private String beginPoint;

    private String endPoint;
}