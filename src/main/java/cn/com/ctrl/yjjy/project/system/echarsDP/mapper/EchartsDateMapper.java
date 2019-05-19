package cn.com.ctrl.yjjy.project.system.echarsDP.mapper;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsDate;
import java.util.List;
/**
 * 时间图表 数据层
 *
 * @author zzmh
 */
public interface EchartsDateMapper {
    /**
     * 查询时间图表
     *
     * @return 时间图表
     */
    List<EchartsDate> selectEchartsDate();
}