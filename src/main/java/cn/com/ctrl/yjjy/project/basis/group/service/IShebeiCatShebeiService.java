package cn.com.ctrl.yjjy.project.basis.group.service;

import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.system.user.entity.EchartsSimple;

import java.util.List;
import java.util.Map;

/**
 * 设备分组关系 服务层
 *
 * @author zzmh
 * @date 2018-12-07
 */
public interface IShebeiCatShebeiService {
    /**
     * 查询设备分组关系信息
     *
     * @param id 设备分组关系ID
     * @return 设备分组关系信息
     */
    ShebeiCatShebei selectShebeiCatShebeiById(String id);

    /**
     * 查询设备分组关系列表
     *
     * @param shebeiCatShebei 设备分组关系信息
     * @return 设备分组关系集合
     */
    List<ShebeiCatShebei> selectShebeiCatShebeiList(ShebeiCatShebei shebeiCatShebei);
    /**
     * 查询设备EchartsSimple
     *
     * @param es 设备分组关系信息
     * @return 设备EchartsSimple
     */
    List<EchartsSimple> selectEchartsSimple(EchartsSimple es);

    /**
     * 新增设备分组关系
     *
     * @param shebeiCatShebei 设备分组关系信息
     * @return 结果
     */
    int insertShebeiCatShebei(ShebeiCatShebei shebeiCatShebei);

    /**
     * 修改设备分组关系
     *
     * @param shebeiCatShebei 设备分组关系信息
     * @return 结果
     */
    int updateShebeiCatShebei(ShebeiCatShebei shebeiCatShebei);

    /**
     * 删除设备分组关系信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteShebeiCatShebeiByIds(String ids);
    /**
     * 查询分组设备列表
     *
     * @param catId 分组设备ID信息
     * @return 设备分组设备列表集合
     */
    List<Shebei> selectListShebeiByCatId(String catId);

    Integer selectshebeiCountByCatId(Map map);
}