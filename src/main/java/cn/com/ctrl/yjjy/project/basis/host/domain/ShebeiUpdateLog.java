package cn.com.ctrl.yjjy.project.basis.host.domain;

import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 设备心跳日志表 p_shebei_update_log
 *
 * @author yjjy
 * @date 2019-04-14
 */
@Data
public class ShebeiUpdateLog extends BaseEntity {
    private static final long serialVersionUID=1L;

/** 设备id */
    private String shebeiId;
/** 设备心跳更新时间 */
    private Date updateTime;
/** 0不通,1在线,2离线播放,3在线播放,4通话中,5紧急广播,6应急逃生 */
    private String status;

}
