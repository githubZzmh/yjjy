package cn.com.ctrl.yjjy.project.material.item.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 救援物资明细表 p_material_item
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
public class Item extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 设备材料名称 */
    private String name;
/** 型号规格 */
    private String specification;
/** 数量 */
    private String sum;
/** 性能 */
    private String performance;
    /**
     * 运输条件
     */
    private String transport;
    /**
     * 存放地点
     */
    private String storage;
    /**
     * 责任人
     */
    private String liable;
    /**
     * 联系方式
     */
    private String contact;
}