package cn.com.ctrl.yjjy.project.basis.group.service;
import java.util.List;
import java.util.Map;

import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import cn.com.ctrl.yjjy.project.system.user.entity.EchartsSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.project.basis.group.mapper.ShebeiCatShebeiMapper;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCatShebei;
import cn.com.ctrl.yjjy.common.support.Convert;

/**
 * 设备分组关系 服务层实现
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
@Log4j2
@Service
public class ShebeiCatShebeiServiceImpl implements IShebeiCatShebeiService {
    @Autowired
    private ShebeiCatShebeiMapper shebeiCatShebeiMapper;

    /**
     * 查询设备分组关系信息
     *
     * @param id 设备分组关系ID
     * @return 设备分组关系信息
     */
    @Override
    public ShebeiCatShebei selectShebeiCatShebeiById(String id) {
        return shebeiCatShebeiMapper.selectShebeiCatShebeiById(id);
    }

    /**
     * 查询设备分组关系列表
     *
     * @param shebeiCatShebei 设备分组关系信息
     * @return 设备分组关系集合
     */
    @Override
    public List<ShebeiCatShebei> selectShebeiCatShebeiList(ShebeiCatShebei shebeiCatShebei) {
        List<ShebeiCatShebei> l = shebeiCatShebeiMapper.selectShebeiCatShebeiList(shebeiCatShebei);
        return l;
    }

    /**
     * 新增设备分组关系
     *
     * @param shebeiCatShebei 设备分组关系信息
     * @return 结果
     */
    @Override
    public int insertShebeiCatShebei(ShebeiCatShebei shebeiCatShebei) {
        return shebeiCatShebeiMapper.insertShebeiCatShebei(shebeiCatShebei);
    }

    /**
     * 修改设备分组关系
     *
     * @param shebeiCatShebei 设备分组关系信息
     * @return 结果
     */
    @Override
    public int updateShebeiCatShebei(ShebeiCatShebei shebeiCatShebei) {
        return shebeiCatShebeiMapper.updateShebeiCatShebei(shebeiCatShebei);
    }

    /**
     * 删除设备分组关系对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteShebeiCatShebeiByIds(String ids) {
        return shebeiCatShebeiMapper.deleteShebeiCatShebeiByIds(Convert.toStrArray(ids));
    }
    /**
     * 查询分组设备列表
     *
     * @param catId 分组设备ID信息
     * @return 设备分组设备列表集合
     */
    public List<Shebei> selectListShebeiByCatId(String catId){
        return shebeiCatShebeiMapper.selectListShebeiByCatId(catId);
    }
    /**
     * 查询设备EchartsSimple
     *
     * @param es 设备分组关系信息
     * @return 设备EchartsSimple
     */
    public List<EchartsSimple> selectEchartsSimple(EchartsSimple es){
        return shebeiCatShebeiMapper.selectEchartsSimple(es);
    }


    @Override
    public Integer selectshebeiCountByCatId(Map map){
        return shebeiCatShebeiMapper.selectshebeiCountByCatId(map);
    }
}