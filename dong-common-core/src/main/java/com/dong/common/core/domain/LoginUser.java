package com.dong.common.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 登录用户身份权限
 * 
 * @author suihan
 */
@Data
public class LoginUser
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    private String username;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户信息
     */
    private SysUsers user;

    private List<String> roles;
}
