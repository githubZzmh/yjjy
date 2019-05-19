package cn.com.ctrl.yjjy.project.system.echarsDP.service;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsList;
import cn.com.ctrl.yjjy.project.system.echarsDP.mapper.EchartsListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 列表图表 业务层处理
 *
 * @author zzmh
 */
@Service
public class EchartsListService {
    @Autowired
    private EchartsListMapper echartsListMapper;
    /**
     * 查询列表图表
     *
     * @return 时间图表
     */
    public List<EchartsList> selectEchartsListByType(String type) {
        return echartsListMapper.selectEchartsListByType(type);
    }
}