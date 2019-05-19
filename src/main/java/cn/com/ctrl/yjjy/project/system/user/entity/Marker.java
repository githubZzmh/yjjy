package cn.com.ctrl.yjjy.project.system.user.entity;
import lombok.Data;

/**
 * 标记
 *
 * @author zzmh
 */
@Data
public class Marker {
    /**
     * 标记ID
     */
    private String sid;
    /**
     * 标记标题
     */
    private String title;
    /**
     * 标记内容
     */
    private String content;
    /**
     * 标记坐标
     */
    private String point;
    /**
     * 标记选项W
     */
    private double iconW;
    /**
     * 标记选项H
     */
    private double iconH;
    /**
     * 标记选项L
     */
    private double iconL;
    /**
     * 标记选项T
     */
    private double iconT;
    /**
     * 标记选项X
     */
    private double iconX;
    /**
     * 标记选项LB
     */
    private double iconLB;
    /**
     * 标记类型
     */
    private String type;
}