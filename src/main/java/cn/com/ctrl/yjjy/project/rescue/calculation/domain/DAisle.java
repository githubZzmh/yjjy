package cn.com.ctrl.yjjy.project.rescue.calculation.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
/**
 * 通道矩阵
 *
 * @author zzmh
 * @date 2018-12-18
 */
@Data
public class DAisle extends BaseEntity {
    /** 起始点number */
    private String ln;
    /** 终止点number */
    private String fn;
    /** 起始点X */
    private String lx;
    /** 起始点Y */
    private String ly;
    /** 终止点X */
    private String fx;
    /** 终止点Y */
    private String fy;
    /** 通道距离 */
    private String myjz;
}