package cn.com.ctrl.yjjy.project.system.echarsDP.service;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsDate;
import cn.com.ctrl.yjjy.project.system.echarsDP.mapper.EchartsDateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
/**
 * 时间图表 业务层处理
 *
 * @author zzmh
 */
@Service
public class EchartsDateService {
    @Autowired
    private EchartsDateMapper echartsDateMapper;
    /**
     * 查询时间图表
     *
     * @return 时间图表
     */
    public List<EchartsDate> selectEchartsDate() {
        return echartsDateMapper.selectEchartsDate();
    }
}