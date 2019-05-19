package cn.com.ctrl.yjjy.project.system.user.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;

/**
 * 预案事故地图路线及播报表 p_plan_map
 *
 * @author zzmh
 * @date 2018-12-27
 */
@Data
public class PlanMap extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 事故日志id */
    private String planId;
/** 设备id */
    private String shebeiId;
/** 事故地图线路图片url */
    private String mapUrl;
/** 事故广播音频url */
    private String broadcastUrl;
/** 图片文件名称 */
    private String mapName;
/** 音频文件名称 */
    private String broadcastName;
/**  */
    private String userid;
/**  */
    private Date time;
}