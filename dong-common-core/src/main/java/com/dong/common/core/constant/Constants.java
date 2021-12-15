package com.dong.common.core.constant;

/**
 * 通用常量信息
 * 
 * @author wdzk
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    public static final Integer TOKEN_EXPIRE = 300;

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";
    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;
    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 后台登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "app_login_tokens:";

    /**
     * 前端登录用户redis key
     */
    public static final String FRONTEND_LOGIN_TOKEN_KEY = "user_token:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "app_login_user_key";


    /**
     * 令牌前缀
     */
    public static final String FRONTEND_LOGIN_USER_KEY = "f_login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";



    /**
     * 正排序 /是否显示
     */
    public static final int N_ONE = 1;

    /**
     * 倒排序
     */
    public static final int DESC = 2;

    /**
     * 限时秒杀
     */
    public static final int LIMITED_TIME_SPIKE = 3;

    /**
     * 赠送类活动信息
     */
    public static final int  GIFT_TYPE_ACTIVE = 2;

    /**
     * 活动参与类型
     */
    public static final int JOIN_NUM_TYPE_EVERY_DEFAULT= 0;  //默认
    public static final int JOIN_NUM_TYPE_EVERY_DAY= 1;  //每天参与
    public static final int JOIN_NUM_TYPE_EVERY_MOUTH= 2;  //每月参与
    public static final int JOIN_NUM_TYPE_EVERY_NEW_USER= 3;  //仅新用户参与

}
