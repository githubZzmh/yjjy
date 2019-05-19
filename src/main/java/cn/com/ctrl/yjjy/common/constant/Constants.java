package cn.com.ctrl.yjjy.common.constant;

/**
 * 通用常量信息
 *
 * @author yjjy
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8" ;

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0" ;

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1" ;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success" ;

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout" ;

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error" ;

    /**
     * 自动去除表前缀
     */
    public static String AUTO_REOMVE_PRE = "true" ;

    /**
     * 当前记录起始索引
     */
    public static String PAGE_NUM = "pageNum" ;

    /**
     * 每页显示记录数
     */
    public static String PAGE_SIZE = "pageSize" ;

    /**
     * 排序列
     */
    public static String ORDER_BY_COLUMN = "orderByColumn" ;

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static String IS_ASC = "isAsc" ;

    //演示系统账户
    public static String DEMO_ACCOUNT = "test" ;
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop" ;
    //开启计划任务
    public static String STATUS_RUNNING_START = "start" ;
    //通知公告阅读状态-未读
    public static String OA_NOTIFY_READ_NO = "0" ;
    //通知公告阅读状态-已读
    public static int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public static Long DEPT_ROOT_ID = 0l;
    //缓存方式
    public static String CACHE_TYPE_REDIS = "redis" ;

    public static String LOG_ERROR = "error" ;
}
