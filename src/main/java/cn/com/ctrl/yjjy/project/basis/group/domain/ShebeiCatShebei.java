package cn.com.ctrl.yjjy.project.basis.group.domain;
import cn.com.ctrl.yjjy.framework.web.domain.BaseEntity;
import cn.com.ctrl.yjjy.project.basis.host.domain.Shebei;
import lombok.Data;

/**
 * 设备分组关系表 p_shebei_cat_shebei
 *
 * @author zzmh
 * @date 2018-12-07
 */
@Data
public class ShebeiCatShebei extends BaseEntity {
    private static final long serialVersionUID=1L;

/**  */
    private String id;
/**  */
    private String name;
/**  */
    private String catId;
/**  */
    private String shebeiId;
    private ShebeiCat shebeiCat;
    private Shebei shebei;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getShebeiId() {
        return shebeiId;
    }

    public void setShebeiId(String shebeiId) {
        this.shebeiId = shebeiId;
    }

    public ShebeiCat getShebeiCat() {
        return shebeiCat;
    }

    public void setShebeiCat(ShebeiCat shebeiCat) {
        this.shebeiCat = shebeiCat;
    }

    public Shebei getShebei() {
        return shebei;
    }

    public void setShebei(Shebei shebei) {
        this.shebei = shebei;
    }
}