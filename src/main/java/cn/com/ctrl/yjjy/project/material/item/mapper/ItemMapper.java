package cn.com.ctrl.yjjy.project.material.item.mapper;
import cn.com.ctrl.yjjy.project.material.item.domain.Item;
import java.util.List;
/**
 * 设备明细
 *
 * @author zzmh
 * @date 2019-01-05
 */
public interface ItemMapper {
    /**
     * 查询救援物资明细列表
     *
     * @param item 救援物资明细信息
     * @return 救援物资明细集合
     */
    List<Item> selectItemList(Item item);
    /**
     * 新增物资明细
     *
     * @param item 物资明细信息
     * @return 结果
     */
    int insertItem(Item item);
    /**
     * 修改物资明细
     *
     * @param item 物资明细信息
     * @return 结果
     */
    int updateItem(Item item);
    /**
     * 删除物资明细
     *
     * @param ids 物资明细ID
     * @return 结果
     */
    int deleteItemById(List<String> ids);
    /**
     * 查询救援物资明细
     *
     * @param id 救援物资明细id
     * @return 救援物资明细
     */
    Item selectItemById(String id);
    /**
     * 查询运输条件
     *
     * @return 运输条件明细
     */
    List<String> selectItemTransport();
    /**
     * 查询存放地点
     *
     * @return 存放地点明细
     */
    List<String> selectItemStorage();
    /**
     * 查询责任人
     *
     * @return 责任人明细
     */
    List<String> selectItemLiable();
}