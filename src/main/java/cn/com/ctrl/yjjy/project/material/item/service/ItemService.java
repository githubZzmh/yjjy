package cn.com.ctrl.yjjy.project.material.item.service;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import cn.com.ctrl.yjjy.project.material.item.domain.Item;
import cn.com.ctrl.yjjy.project.material.item.mapper.ItemMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 设备明细 服务层实现
 *
 * @author zzmh
 * @date 2018-11-30
 */
@Data
@Log4j2
@Service("item")
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;
    /**
     * 查询救援物资明细列表
     *
     * @param item 救援物资明细信息
     * @return 救援物资明细集合
     */
    public List<Item> selectItemList(Item item) {
        return itemMapper.selectItemList(item);
    }
    /**
     * 新增物资明细
     *
     * @param item 物资明细信息
     * @return 结果
     */
    public int insertItem(Item item) {
        return itemMapper.insertItem(item);
    }
    /**
     * 修改物资明细
     *
     * @param item 物资明细信息
     * @return 结果
     */
    public int updateItem(Item item) {
        return itemMapper.updateItem(item);
    }
    /**
     * 删除物资明细
     *
     * @param ids 物资明细ID
     * @return 结果
     */
    public int deleteItemById(List<String> ids) {
        return itemMapper.deleteItemById(ids);
    }
    /**
     * 查询救援物资明细
     *
     * @param id 救援物资明细id
     * @return 救援物资明细
     */
    public Item selectItemById(String id) {
        return itemMapper.selectItemById(id);
    }
    /**
     * 查询运输条件
     *
     * @return 运输条件明细
     */
    public List<String> selectItemTransport() {
        return itemMapper.selectItemTransport();
    }
    /**
     * 查询存放地点
     *
     * @return 存放地点明细
     */
    public List<String> selectItemStorage() {
        return itemMapper.selectItemStorage();
    }
    /**
     * 查询责任人
     *
     * @return 责任人明细
     */
    public List<String> selectItemLiable() {
        return itemMapper.selectItemLiable();
    }
}