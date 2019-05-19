package cn.com.ctrl.yjjy.project.system.echarsDP.mapper;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsList;
import java.util.List;
/**
 * 列表图表 数据层
 *
 * @author zzmh
 */
public interface EchartsListMapper {
    /**
     * 查询列表图表
     *
     * @return 列表图表
     */
    List<EchartsList> selectEchartsListByType(String type);
}