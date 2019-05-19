package cn.com.ctrl.yjjy.project.basis.group.mapper;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;

import java.util.List;

/**
 * 设备分组 数据层
 *
 * @author zzmh
 * @date 2018-11-30
 */
public interface ShebeiCatMapper {
    /**
     * 查询设备分组信息
     *
     * @param id 设备分组ID
     * @return 设备分组信息
     */
    ShebeiCat selectShebeiCatById(String id);
    /**
     * 查询设备分组列表
     *
     * @param shebeiCat 设备分组信息
     * @return 设备分组集合
     */
    List<ShebeiCat> selectShebeiCatList(ShebeiCat shebeiCat);
    /**
     * 新增设备分组
     *
     * @param shebeiCat 设备分组信息
     * @return 结果
     */
    int insertShebeiCat(ShebeiCat shebeiCat);
    /**
     * 修改设备分组
     *
     * @param shebeiCat 设备分组信息
     * @return 结果
     */
    int updateShebeiCat(ShebeiCat shebeiCat);
    /**
     * 删除设备分组
     *
     * @param id 设备分组ID
     * @return 结果
     */
    int deleteShebeiCatById(String id);
    /**
     * 批量删除设备分组
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteShebeiCatByIds(String[] ids);
    /**
     * 查询类型设备集合
     *
     * @return 结果
     */
    List<ShebeiCat> selectShebeiCatByType(String type);
}