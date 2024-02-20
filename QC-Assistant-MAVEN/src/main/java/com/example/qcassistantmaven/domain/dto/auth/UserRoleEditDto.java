package com.example.qcassistantmaven.domain.dto.auth;

public class UserRoleEditDto {

    private String username;

    private String roleLevel;



    public String getUsername() {
        return username;
    }

    public UserRoleEditDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    public UserRoleEditDto setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel;
        return this;
    }
}
