package cn.com.ctrl.yjjy.project.system.echarsDP.mapper;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsCd;
import java.util.List;
/**
 * 呼叫掉线图表 数据层
 *
 * @author zzmh
 */
public interface EchartsCdMapper {
    /**
     * 查询呼叫掉线图表
     *
     * @return 呼叫掉线图表
     */
    List<EchartsCd> selectEchartsCd(String type);
}