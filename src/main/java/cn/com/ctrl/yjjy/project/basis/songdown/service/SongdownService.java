package cn.com.ctrl.yjjy.project.basis.songdown.service;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.mapper.ShebeiMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 设备 服务层实现
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Service
public class SongdownService{
    @Autowired
    private ShebeiMapper shebeiMapper;
    /**
     * 查询设备信息
     *
     * @param id 设备ID
     * @return 设备信息
     */
    public Shebei selectShebeiById(String id) {
        return shebeiMapper.selectShebeiById(id);
    }
    /**
     * 查询设备列表
     *
     * @param shebei 设备信息
     * @return 设备集合
     */
    public List<Shebei> selectShebeiList(Shebei shebei) {
        return shebeiMapper.selectShebeiList(shebei);
    }
}