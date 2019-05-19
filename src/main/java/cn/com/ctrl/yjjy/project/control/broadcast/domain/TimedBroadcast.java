package cn.com.ctrl.yjjy.project.control.broadcast.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;
/**
 * 定时广播表 p_timed_broadcast
 *
 * @author zzmh
 * @date 2018-12-11
 */
@Data
public class TimedBroadcast extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 规则名称 */
    private String name;
/** 创建人 */
    private String creatId;
/** 创建时间 */
    private Date creatTime;
}