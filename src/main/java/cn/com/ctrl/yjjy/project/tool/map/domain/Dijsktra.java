package cn.com.ctrl.yjjy.project.tool.map.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import lombok.Data;
/**
 * 标点矩阵表 p_dijsktra
 *
 * @author zzmh
 * @date 2018-12-31
 */
@Data
public class Dijsktra extends BaseEntity {
    private static final long serialVersionUID=1L;
/**  */
    private String id;
/** 标点id */
    private String pointId;
/** 标点矩阵编号 */
    private String weightNum;
/** 是否矩阵原点 */
    private String opstatus;
/** 连域编号组 */
    private String larr;
/** 全域编号组 */
    private String alarr;
    /** 标点名称 */
    private String pointName;


    private String pName;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}