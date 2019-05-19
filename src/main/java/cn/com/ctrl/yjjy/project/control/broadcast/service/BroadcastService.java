package cn.com.ctrl.yjjy.project.control.broadcast.service;
import cn.com.ctrl.yjjy.common.support.Convert;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.mapper.ShebeiCatMapper;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.ShebeiCatType;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.TimedBroadcast;
import cn.com.ctrl.yjjy.project.control.broadcast.mapper.ShebeiCatTypeMapper;
import cn.com.ctrl.yjjy.project.control.broadcast.mapper.TimedBroadcastMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备分组 服务层实现
 *
 * @author zzmh
 * @date 2018-11-30
 */
@Data
@Log4j2
@Service("broadcast")
public class BroadcastService {
    @Autowired
    private TimedBroadcastMapper timedBroadcastMapper;
    @Autowired
    private ShebeiCatTypeMapper shebeiCatTypeMapper;
    @Autowired
    private ShebeiCatMapper shebeiCatMapper;

    /**
     * 查询定时广播信息
     *
     * @return 定时广播信息
     */
    public List<TimedBroadcast> selectListTimedBroadcast(TimedBroadcast timedBroadcast) {
        List<TimedBroadcast> listTimedBroadcast = timedBroadcastMapper.selectTimedBroadcastList(timedBroadcast);
        return listTimedBroadcast;
    }
    /**
     * 查询定时广播信息
     *
     * @return 定时广播信息
     */
    public TimedBroadcast selectTimedBroadcastById(String id) {
        TimedBroadcast timedBroadcast = timedBroadcastMapper.selectTimedBroadcastById(id);
        return timedBroadcast;
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
    /**
     * 新增定时广播
     *
     * @param timedBroadcast 定时广播信息
     * @return 结果
     */
    public int insertTimedBroadcast(TimedBroadcast timedBroadcast) {
        return timedBroadcastMapper.insertTimedBroadcast(timedBroadcast);
    }
    /**
     * 修改定时广播
     *
     * @param timedBroadcast 定时广播信息
     * @return 结果
     */
    public int updateTimedBroadcast(TimedBroadcast timedBroadcast){
        return timedBroadcastMapper.updateTimedBroadcast(timedBroadcast);
    }
    /**
     * 查询设备分组类型
     *
     * @return 结果
     */
    public List<ShebeiCatType> selectShebeiCatTypeList() {
        //根据类型查询列表
        ShebeiCatType ShebeiCatType_0 = new ShebeiCatType();
        ShebeiCatType ShebeiCatType_1 = new ShebeiCatType();
        ShebeiCatType ShebeiCatType_2 = new ShebeiCatType();
        //0 广播小组  1 音乐小组 2应急小组
        ShebeiCatType_0.setType("广播小组");
        ShebeiCatType_0.setShebeiCatList(shebeiCatMapper.selectShebeiCatByType("0"));
        ShebeiCatType_1.setType("音乐小组");
        ShebeiCatType_1.setShebeiCatList(shebeiCatMapper.selectShebeiCatByType("1"));
        ShebeiCatType_2.setType("应急小组");
        ShebeiCatType_2.setShebeiCatList(shebeiCatMapper.selectShebeiCatByType("2"));
        //set值
        List<ShebeiCatType> shebeiCatTypeList = new ArrayList<ShebeiCatType>();
        shebeiCatTypeList.add(ShebeiCatType_0);
        shebeiCatTypeList.add(ShebeiCatType_1);
        shebeiCatTypeList.add(ShebeiCatType_2);
        return shebeiCatTypeList;
    }
    /**
     * 删除规则
     *
     * @param ids 规则id
     * @return 结果
     */
    public int deleteTimedBroadcastByIds(String ids){
        return timedBroadcastMapper.deleteTimedBroadcastByIds(Convert.toStrArray(ids));
    }
}