package cn.com.ctrl.yjjy.project.basis.host.service;

import java.util.List;
import java.util.Map;

import cn.com.ctrl.yjjy.project.basis.host.domain.ShebeiUpdateLog;
import cn.com.ctrl.yjjy.project.basis.host.mapper.ShebeiUpdateLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.basis.host.mapper.ShebeiMapper;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 设备 服务层实现
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Service
public class ShebeiServiceImpl implements IShebeiService {
    @Autowired
    private ShebeiMapper shebeiMapper;
    @Autowired
    private ShebeiUpdateLogMapper shebeiUpdateLogMapper;

    /**
     * 查询设备信息
     *
     * @param id 设备ID
     * @return 设备信息
     */
    @Override
    public Shebei selectShebeiById(String id) {
        return shebeiMapper.selectShebeiById(id);
    }

    /**
     * 查询设备列表
     *
     * @param shebei 设备信息
     * @return 设备集合
     */
    @Override
    public List<Shebei> selectShebeiList(Shebei shebei) {
        return shebeiMapper.selectShebeiList(shebei);
    }

    /**
     * 新增设备
     *
     * @param shebei 设备信息
     * @return 结果
     */
    @Override
    public int insertShebei(Shebei shebei) {
        return shebeiMapper.insertShebei(shebei);
    }

    /**
     * 修改设备
     *
     * @param shebei 设备信息
     * @return 结果
     */
    @Override
    public int updateShebei(Shebei shebei) {
        return shebeiMapper.updateShebei(shebei);
    }

    /**
     * 删除设备对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteShebeiByIds(String ids) {
        return shebeiMapper.deleteShebeiByIds(Convert.toStrArray(ids));
    }
    /**
     * 发送设备对象
     *
     * @param ids 需要发送的数据ID
     * @return 结果
     */
    @Override
    public int updateShebeiByIds(String ids) {
        return shebeiMapper.updateShebeiByIds(Convert.toStrArray(ids));
    }

    @Override
    public int selectShebeiCountByStatus(Map map){
        return shebeiMapper.selectShebeiCountByStatus(map);
    }

    @Override
    public List<Shebei> selectShebeiByIds(List<String> ls){
        return shebeiMapper.selectShebeiByIds(ls);
    }

    @Override
    public List<Shebei> selectShebeiDp(){
        return shebeiMapper.selectShebeiDp();
    }
    @Override
    public List<Shebei> selectShebeis(){
        return shebeiMapper.selectShebeis();
    }
    @Override
    public int selectOnlineCount(ShebeiUpdateLog shebeiUpdateLog){
        return shebeiUpdateLogMapper.selectOnlineCount(shebeiUpdateLog);
    }
    @Override
    public int selectDownCount(ShebeiUpdateLog shebeiUpdateLog){
        return shebeiUpdateLogMapper.selectDownCount(shebeiUpdateLog);
    }

    @Override
    public int selectEveryDayCount(Map map){
        return shebeiUpdateLogMapper.selectEveryDayCount(map);
    }
}
