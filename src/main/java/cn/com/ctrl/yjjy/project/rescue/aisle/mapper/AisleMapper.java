package cn.com.ctrl.yjjy.project.rescue.aisle.mapper;

import cn.com.ctrl.yjjy.project.rescue.aisle.domain.Aisle;
import java.util.List;

/**
 * 通道 数据层
 *
 * @author zzmh
 * @date 2018-12-18
 */
public interface AisleMapper {
    /**
     * 查询通道信息
     *
     * @param id 通道ID
     * @return 通道信息
     */
    Aisle selectAisleById(String id);

    /**
     * 查询通道列表
     *
     * @param aisle 通道信息
     * @return 通道集合
     */
    List<Aisle> selectAisleList(Aisle aisle);
    /**
     * 查询通道列表
     *
     * @param aisle 通道信息
     * @return 通道集合
     */
    List<Aisle> selectAisleList_(Aisle aisle);

    /**
     * 新增通道
     *
     * @param aisle 通道信息
     * @return 结果
     */
    int insertAisle(Aisle aisle);

    /**
     * 修改通道
     *
     * @param aisle 通道信息
     * @return 结果
     */
    int updateAisle(Aisle aisle);

    /**
     * 删除通道
     *
     * @param id 通道ID
     * @return 结果
     */
    int deleteAisleById(String id);

    int deleteAisleByGroupId(String id);

    /**
     * 批量删除通道
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAisleByIds(String[] ids);
    /**
     * 批量占用通道
     *
     * @param ids 需要占用的数据ID
     * @return 结果
     */
    List<Aisle> selectAisleByIds(String[] ids);
    /**
     * 计算完成
     *
     * @return 结果
     */
    int uverAisle();
}