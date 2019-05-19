package cn.com.ctrl.yjjy.project.system.echarsDP.entity;
import lombok.Data;
import java.util.List;
/**
 * echarts掉线图表
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
public class EchartsDrop {
    //设备名
    private List<String> device;
    //宕机次数
    private List<String> data_;
}