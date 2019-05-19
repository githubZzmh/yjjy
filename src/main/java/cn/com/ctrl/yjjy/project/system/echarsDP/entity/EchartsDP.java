package cn.com.ctrl.yjjy.project.system.echarsDP.entity;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import lombok.Data;
import java.util.List;
/**
 * echarts大屏数据
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
public class EchartsDP {
    //时间图表
    private List<EchartsDate> listEchartsDate;
    //故障图表
    private EchartsDevice echartsDevice;
    //列表图表
    private List<Shebei> listEchartsList;
    //呼叫图表
    private EchartsCall echartsCall;
    //掉线图表
    private EchartsDrop echartsDrop;
}