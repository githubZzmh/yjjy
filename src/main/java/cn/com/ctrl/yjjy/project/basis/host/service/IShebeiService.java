package cn.com.ctrl.yjjy.project.basis.host.service;

import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.basis.host.domain.ShebeiUpdateLog;

import java.util.List;
import java.util.Map;

/**
 * 设备 服务层
 *
 * @author zzmh
 * @date 2018-11-28
 */
public interface IShebeiService {
    /**
     * 查询设备信息
     *
     * @param id 设备ID
     * @return 设备信息
     */
    Shebei selectShebeiById(String id);

    /**
     * 查询设备列表
     *
     * @param shebei 设备信息
     * @return 设备集合
     */
    List<Shebei> selectShebeiList(Shebei shebei);

    /**
     * 新增设备
     *
     * @param shebei 设备信息
     * @return 结果
     */
    int insertShebei(Shebei shebei);

    /**
     * 修改设备
     *
     * @param shebei 设备信息
     * @return 结果
     */
    int updateShebei(Shebei shebei);

    /**
     * 删除设备信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteShebeiByIds(String ids);
    /**
     * 发送设备信息
     *
     * @param ids 需要发送的数据ID
     * @return 结果
     */
    int updateShebeiByIds(String ids);

    int selectShebeiCountByStatus(Map map);

    List<Shebei> selectShebeiByIds(List<String> ls);

    List<Shebei> selectShebeiDp();
    List<Shebei> selectShebeis();

    int selectOnlineCount(ShebeiUpdateLog shebeiUpdateLog);

    int selectDownCount(ShebeiUpdateLog shebeiUpdateLog);

    int selectEveryDayCount(Map map);
}
