package cn.com.ctrl.yjjy.project.control.broadcast.mapper;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.TimedBroadcast;
import java.util.List;
/**
 * 定时广播 数据层
 *
 * @author zzmh
 * @date 2018-12-11
 */
public interface TimedBroadcastMapper {
    /**
     * 查询定时广播信息
     *
     * @param id 定时广播ID
     * @return 定时广播信息
     */
    TimedBroadcast selectTimedBroadcastById(String id);
    /**
     * 查询定时广播列表
     *
     * @param timedBroadcast 定时广播信息
     * @return 定时广播集合
     */
    List<TimedBroadcast> selectTimedBroadcastList(TimedBroadcast timedBroadcast);
    /**
     * 新增定时广播
     *
     * @param timedBroadcast 定时广播信息
     * @return 结果
     */
    int insertTimedBroadcast(TimedBroadcast timedBroadcast);
    /**
     * 修改定时广播
     *
     * @param timedBroadcast 定时广播信息
     * @return 结果
     */
    int updateTimedBroadcast(TimedBroadcast timedBroadcast);
    /**
     * 删除定时广播
     *
     * @param id 定时广播ID
     * @return 结果
     */
    int deleteTimedBroadcastById(String id);
    /**
     * 批量删除定时广播
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTimedBroadcastByIds(String[] ids);
}