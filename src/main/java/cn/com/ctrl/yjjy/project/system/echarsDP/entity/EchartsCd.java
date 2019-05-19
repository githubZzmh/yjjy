package cn.com.ctrl.yjjy.project.system.echarsDP.entity;
import lombok.Data;

/**
 * echarts呼叫掉线图表
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
public class EchartsCd {
    //设备名
    private String device;
    //呼叫次数
    private String data;
    //宕机次数
    private String data_;
}