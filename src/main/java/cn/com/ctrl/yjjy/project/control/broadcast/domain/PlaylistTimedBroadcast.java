package cn.com.ctrl.yjjy.project.control.broadcast.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.playlist.domain.Playlist;
import lombok.Data;
import java.util.Date;
/**
 * 定时广播关系表 p_playlist_timed_broadcast
 *
 * @author yjjy
 * @date 2018-12-13
 */
@Data
public class PlaylistTimedBroadcast extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 定时广播表id */
    private String broadcastId;
/** 播放列表id */
    private String playlistId;
    /** 设备分组id */
    private String shebeiCatId;
    /** 开始时间 */
    private Date begintime;
    /** 结束时间 */
    private Date endtime;
    private TimedBroadcast timedBroadcast;
    private Playlist playlist;
    private ShebeiCat shebeiCat;
}