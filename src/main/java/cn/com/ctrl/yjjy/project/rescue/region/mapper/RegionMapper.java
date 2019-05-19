package cn.com.ctrl.yjjy.project.rescue.region.mapper;

import cn.com.ctrl.yjjy.project.rescue.region.domain.Region;
import java.util.List;

/**
 * 区域 数据层
 *
 * @author yjjy
 * @date 2019-03-14
 */
public interface RegionMapper {
    /**
     * 查询区域信息
     *
     * @param id 区域ID
     * @return 区域信息
     */
        Region selectRegionById(String id);

    /**
     * 查询区域列表
     *
     * @param region 区域信息
     * @return 区域集合
     */
    List<Region> selectRegionList(Region region);

    /**
     * 新增区域
     *
     * @param region 区域信息
     * @return 结果
     */
    int insertRegion(Region region);

    /**
     * 修改区域
     *
     * @param region 区域信息
     * @return 结果
     */
    int updateRegion(Region region);

    /**
     * 删除区域
     *
     * @param id 区域ID
     * @return 结果
     */
    int deleteRegionById(String id);

    /**
     * 批量删除区域
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRegionByIds(String[] ids);

}