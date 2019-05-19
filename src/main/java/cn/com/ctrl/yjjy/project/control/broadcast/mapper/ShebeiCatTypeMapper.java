package cn.com.ctrl.yjjy.project.control.broadcast.mapper;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.ShebeiCatType;
import cn.com.ctrl.yjjy.project.control.broadcast.domain.TimedBroadcast;
import java.util.List;
/**
 * 设备分组 数据层
 *
 * @author zzmh
 * @date 2018-12-11
 */
public interface ShebeiCatTypeMapper {
    /**
     * 查询设备分组列表
     *
     * @param shebeiCatType 设备分组信息
     * @return 设备分组集合
     */
    List<ShebeiCatType> selectShebeiCatTypeList(ShebeiCatType shebeiCatType);
}