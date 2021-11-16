package com.dong.common.core.domain;


import com.dong.common.core.web.domain.BaseEntity;


public class Users  extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private String userName;
    private Long clientId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
