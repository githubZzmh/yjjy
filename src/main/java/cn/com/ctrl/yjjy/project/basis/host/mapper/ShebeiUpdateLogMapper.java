package cn.com.ctrl.yjjy.project.basis.host.mapper;


import cn.com.ctrl.yjjy.project.basis.host.domain.ShebeiUpdateLog;

import java.util.List;
import java.util.Map;

/**
 * 设备心跳日志 数据层
 *
 * @author yjjy
 * @date 2019-04-14
 */
public interface ShebeiUpdateLogMapper {
    /**
     * 查询设备心跳日志信息
     *
     * @param shebeiId 设备心跳日志ID
     * @return 设备心跳日志信息
     */
        ShebeiUpdateLog selectShebeiUpdateLogById(String shebeiId);

    /**
     * 查询设备心跳日志列表
     *
     * @param shebeiUpdateLog 设备心跳日志信息
     * @return 设备心跳日志集合
     */
    List<ShebeiUpdateLog> selectShebeiUpdateLogList(ShebeiUpdateLog shebeiUpdateLog);

    /**
     * 新增设备心跳日志
     *
     * @param shebeiUpdateLog 设备心跳日志信息
     * @return 结果
     */
    int insertShebeiUpdateLog(ShebeiUpdateLog shebeiUpdateLog);

    /**
     * 修改设备心跳日志
     *
     * @param shebeiUpdateLog 设备心跳日志信息
     * @return 结果
     */
    int updateShebeiUpdateLog(ShebeiUpdateLog shebeiUpdateLog);

    /**
     * 删除设备心跳日志
     *
     * @param shebeiId 设备心跳日志ID
     * @return 结果
     */
    int deleteShebeiUpdateLogById(String shebeiId);

    /**
     * 批量删除设备心跳日志
     *
     * @param shebeiIds 需要删除的数据ID
     * @return 结果
     */
    int deleteShebeiUpdateLogByIds(String[] shebeiIds);

    int selectOnlineCount(ShebeiUpdateLog shebeiUpdateLog);

    int selectDownCount(ShebeiUpdateLog shebeiUpdateLog);

    int selectEveryDayCount(Map map);

}