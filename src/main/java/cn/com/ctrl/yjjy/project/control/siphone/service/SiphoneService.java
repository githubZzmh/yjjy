package cn.com.ctrl.yjjy.project.control.siphone.service;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.mapper.ShebeiCatMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 设备分组 服务层实现
 *
 * @author zzmh
 * @date 2018-11-30
 */
@Data
@Log4j2
@Service("siphone")
public class SiphoneService {
    @Autowired
    private ShebeiCatMapper shebeiCatMapper;

    /**
     * 查询播放类型信息
     *
     * @return 播放类型信息
     */
    public List<ShebeiCat> selectListShebeiCat() {
        List<ShebeiCat> listShebeiCat = shebeiCatMapper.selectShebeiCatList(null);
        return listShebeiCat;
    }
    /**
     * 播放设备对象
     *
     * @param ids 需要播放的数据ID
     * @return 结果
     */
    public int updateShebeiByIds(String ids) {
        return 1;//shebeiMapper.updateShebeiByIds(Convert.toStrArray(ids));
    }
}