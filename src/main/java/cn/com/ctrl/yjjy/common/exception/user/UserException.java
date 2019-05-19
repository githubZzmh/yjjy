package cn.com.ctrl.yjjy.common.exception.user;

import cn.com.ctrl.yjjy.common.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author yjjy
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user" , code, args, null);
    }

}
