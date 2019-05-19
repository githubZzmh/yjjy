package cn.com.ctrl.yjjy.project.control.plan.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 设备标点地图表 p_shebei_points
 *
 * @author zzmh
 * @date 2019-01-07
 */
@Data
public class ShebeiPoints extends BaseEntity {
    private static final long serialVersionUID=1L;

/** 设备连接通过路径 */
    private String sPoints;
/** 图片名称 */
    private String mapName;
/** 地图图片路径 */
    private String mapUrl;
/** 广播语音名称 */
    private String broadName;
/** 广播语音路径 */
    private String broadUrl;
    /** 路线 */
    private String route;
}