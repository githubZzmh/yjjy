package cn.com.ctrl.yjjy.framework.shiro.service;

import cn.com.ctrl.yjjy.common.constant.Constants;
import cn.com.ctrl.yjjy.common.constant.ShiroConstants;
import cn.com.ctrl.yjjy.common.constant.UserConstants;
import cn.com.ctrl.yjjy.common.exception.user.*;
import cn.com.ctrl.yjjy.common.utils.DateUtils;
import cn.com.ctrl.yjjy.common.utils.MessageUtils;
import cn.com.ctrl.yjjy.common.utils.ServletUtils;
import cn.com.ctrl.yjjy.common.utils.security.ShiroUtils;
import cn.com.ctrl.yjjy.framework.manager.AsyncManager;
import cn.com.ctrl.yjjy.framework.manager.factory.AsyncFactory;
import cn.com.ctrl.yjjy.project.system.user.domain.User;
import cn.com.ctrl.yjjy.project.system.user.domain.UserStatus;
import cn.com.ctrl.yjjy.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录校验方法
 *
 * @author yjjy
 */
@Component
public class LoginService {
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private IUserService userService;

    /**
     * 登录
     */
    public User login(String username, String password) {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 查询用户信息
        User user = userService.selectUserByLoginName(username);

        if (user == null && maybeMobilePhoneNumber(username)) {
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username)) {
            user = userService.selectUserByEmail(username);
        }

        if (user == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }

        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new UserDeleteException();
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked" , user.getRemark())));
            throw new UserBlockedException(user.getRemark());
        }

        passwordService.validate(user, password);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);
        return user;
    }

    private boolean maybeEmail(String username) {
        return username.matches(UserConstants.EMAIL_PATTERN);
    }

    private boolean maybeMobilePhoneNumber(String username) {
        return username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN);
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(User user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }
}
