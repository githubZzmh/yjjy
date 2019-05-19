package cn.com.ctrl.yjjy.project.system.echarsDP.entity;
import lombok.Data;
import java.util.List;
/**
 * echarts呼叫图表
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
public class EchartsCall {
    //设备名
    private List<String> device;
    //呼叫次数
    private List<String> data;
}