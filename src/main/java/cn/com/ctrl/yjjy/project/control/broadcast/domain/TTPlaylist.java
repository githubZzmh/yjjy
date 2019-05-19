package cn.com.ctrl.yjjy.project.control.broadcast.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;
/**
 * 定时广播关系表 test
 *
 * @author yjjy
 * @date 2018-12-13
 */
@Data
public class TTPlaylist {
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
    private String begintime;
    /** 结束时间 */
    private String endtime;
}
