package cn.com.ctrl.yjjy.project.basis.songdown.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
/**
 * 设备音乐表 p_shebei_music
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
public class SMusic extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 音乐id */
    private String musicId;
/** 设备id */
    private String shebeiId;
/**  */
    private String createBy;
/**  */
    private Date createTime;
/** 设备横坐标 */
    private String isDel;


    private String[] musicIds;
}