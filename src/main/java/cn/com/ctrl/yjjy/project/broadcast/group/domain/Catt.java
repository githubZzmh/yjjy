package cn.com.ctrl.yjjy.project.broadcast.group.domain;
import lombok.Data;

import java.util.List;

/**
 * 设
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
public class Catt {
    private static final long serialVersionUID=1L;
/**  */
    private Shebeit GroupIP;
/** 组*/
    private List<Shebeit> IPModelList;
}