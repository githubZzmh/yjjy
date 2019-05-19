package cn.com.ctrl.yjjy.project.basis.group.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
/**
 * 设备分组表 p_shebei_cat
 *
 * @author zzmh
 * @date 2018-11-30
 */
@Data
public class ShebeiCat extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 分类名称 */
    private String name;
/** 0 广播小组  1 音乐小组 2应急小组 */
    private String type;
/** 组播地址 */
    private String ip;
/** 播放类型 */
    private String playType;
/** 备注 */
    private String remark;
}