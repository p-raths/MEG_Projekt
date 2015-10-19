package com.example.hsr.meg_projekt.service;

/**
 * Created by praths on 19.10.15.
 */
public class LoginToken {
    private String customerId;
    private String securityToken;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}