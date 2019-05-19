package cn.com.ctrl.yjjy.project.system.user.entity;
import java.util.List;
import lombok.Data;
/**
 * map通道
 *
 * @author zzmh
 */
@Data
public class Polyline {
    /**
     * 通道样式
     */
    private String style;
    /**
     * 通道宽度
     */
    private int weight;
    /**
     * 通道颜色
     */
    private String color;
    /**
     * 通道透明度0-1
     */
    private double opacity;
    /**
     * 通道节点集合
     */
    private List<String> points;
}