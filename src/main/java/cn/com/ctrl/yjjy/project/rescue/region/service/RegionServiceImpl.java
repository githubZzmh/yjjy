package cn.com.ctrl.yjjy.project.rescue.region.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.rescue.region.mapper.RegionMapper;
import cn.com.ctrl.yjjy.project.rescue.region.domain.Region;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 区域 服务层实现
 *
 * @author yjjy
 * @date 2019-03-14
 */
@Data
@Log4j2
@Service
public class RegionServiceImpl implements IRegionService {
    @Autowired
    private RegionMapper regionMapper;

    /**
     * 查询区域信息
     *
     * @param id 区域ID
     * @return 区域信息
     */
    @Override
    public Region selectRegionById(String id) {
        return regionMapper.selectRegionById(id);
    }

    /**
     * 查询区域列表
     *
     * @param region 区域信息
     * @return 区域集合
     */
    @Override
    public List<Region> selectRegionList(Region region) {
        return regionMapper.selectRegionList(region);
    }

    /**
     * 新增区域
     *
     * @param region 区域信息
     * @return 结果
     */
    @Override
    public int insertRegion(Region region) {
        return regionMapper.insertRegion(region);
    }

    /**
     * 修改区域
     *
     * @param region 区域信息
     * @return 结果
     */
    @Override
    public int updateRegion(Region region) {
        return regionMapper.updateRegion(region);
    }

    /**
     * 删除区域对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRegionByIds(String ids) {
        return regionMapper.deleteRegionByIds(Convert.toStrArray(ids));
    }

}
