package cn.com.ctrl.yjjy.project.basis.playlist.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import java.util.Date;

import lombok.Data;
    
/**
 * 播放列表 p_playlist
 *
 * @author zzmh
 * @date 2018-12-04
 */
@Data
public class Playlist extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 播放列表名称 */
    private String name;
/** 备注 */
    private String remark;
/** 开始时间 */
    private Date begintime;
/** 结束时间 */
    private Date endtime;
}