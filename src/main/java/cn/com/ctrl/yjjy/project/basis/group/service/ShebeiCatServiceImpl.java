package cn.com.ctrl.yjjy.project.basis.group.service;
import java.util.List;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.basis.group.mapper.ShebeiCatMapper;
import cn.com.ctrl.yjjy.project.basis.group.mapper.ShebeiCatShebeiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import cn.com.ctrl.yjjy.common.support.Convert;
/**
 * 设备分组 服务层实现
 *
 * @author yjjy
 * @date 2018-11-30
 */
@Data
@Log4j2
@Service
public class ShebeiCatServiceImpl implements IShebeiCatService {
    @Autowired
    private ShebeiCatMapper shebeiCatMapper;
    @Autowired
    private ShebeiCatShebeiMapper shebeiCatShebeiMapper;

    /**
     * 查询设备分组信息
     *
     * @param id 设备分组ID
     * @return 设备分组信息
     */
    @Override
    public ShebeiCat selectShebeiCatById(String id) {
        return shebeiCatMapper.selectShebeiCatById(id);
    }

    /**
     * 查询设备分组列表
     *
     * @param shebeiCat 设备分组信息
     * @return 设备分组集合
     */
    @Override
    public List<ShebeiCat> selectShebeiCatList(ShebeiCat shebeiCat) {
        return shebeiCatMapper.selectShebeiCatList(shebeiCat);
    }

    /**
     * 新增设备分组
     *
     * @param shebeiCat 设备分组信息
     * @return 结果
     */
    @Override
    public int insertShebeiCat(ShebeiCat shebeiCat) {
        return shebeiCatMapper.insertShebeiCat(shebeiCat);
    }

    /**
     * 修改设备分组
     *
     * @param shebeiCat 设备分组信息
     * @return 结果
     */
    @Override
    public int updateShebeiCat(ShebeiCat shebeiCat) {
        return shebeiCatMapper.updateShebeiCat(shebeiCat);
    }

    /**
     * 删除设备分组对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteShebeiCatByIds(String ids) throws Exception {
        String[] shebeiCatIds = Convert.toStrArray(ids);
        for (String shebeiCatId : shebeiCatIds) {
            ShebeiCat shebeiCat = selectShebeiCatById(shebeiCatId);
            if (shebeiCatShebeiMapper.countShebeiCatShebeiByShebeiCatId(shebeiCatId) > 0) {
                throw new Exception(String.format("设备分组%1$s已分配设备,不能删除" , shebeiCat.getName()));
            }
        }
        return shebeiCatMapper.deleteShebeiCatByIds(shebeiCatIds);
    }
}