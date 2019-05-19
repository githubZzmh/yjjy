package cn.com.ctrl.yjjy.project.control.broadcast.domain;
import cn.com.ctrl.yjjy.project.basis.group.domain.ShebeiCat;
import lombok.Data;
import java.util.List;

/**
 * 设备分组类型
 *
 * @author zzmh
 * @date 2018-12-11
 */
@Data
public class ShebeiCatType {
    private String type;
    private List<ShebeiCat> shebeiCatList;
}