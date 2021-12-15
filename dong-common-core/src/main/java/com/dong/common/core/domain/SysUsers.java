package com.dong.common.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysUsers  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String username;

    private String password;

    private String avatar;

    private String telephone;

    private String lastLoginIp;

    private Date lastLoginTime;

    @TableField("merchantCode")
    private String merchantcode;

    @TableField("roleId")
    private String roleid;

    private String role;


}

