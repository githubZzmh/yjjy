package cn.com.ctrl.yjjy.project.system.echarsDP.entity;
import lombok.Data;
/**
 * echarts时间图表
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
public class EchartsDate {
    //时间
    private String date;
    //呼叫次数
    private String data_hujiao;
    //宕机次数
    private String data_dangji;
}