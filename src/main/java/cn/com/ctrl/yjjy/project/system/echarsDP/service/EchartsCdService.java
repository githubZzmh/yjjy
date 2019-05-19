package cn.com.ctrl.yjjy.project.system.echarsDP.service;
import cn.com.ctrl.yjjy.project.system.echarsDP.entity.EchartsCd;
import cn.com.ctrl.yjjy.project.system.echarsDP.mapper.EchartsCdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 时间图表 业务层处理
 *
 * @author zzmh
 */
@Service
public class EchartsCdService {
    @Autowired
    private EchartsCdMapper echartsCdMapper;
    /**
     * 查询呼叫掉线图表
     *
     * @return 呼叫掉线图表
     */
    public List<EchartsCd> selectEchartsCd(String type) {
        return echartsCdMapper.selectEchartsCd(type);
    }
}