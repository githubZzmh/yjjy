package cn.com.ctrl.yjjy.project.rescue.region.domain;

import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 区域表 p_region
 *
 * @author yjjy
 * @date 2019-03-14
 */
@Data
public class Region extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/** 区域名称 */
    private String name;
/** 状态0:可用1:不可用 */
    private String status;
/** 所属楼层0:井下一层1:井下二层2:井下三层 */
    private String floor;

}
