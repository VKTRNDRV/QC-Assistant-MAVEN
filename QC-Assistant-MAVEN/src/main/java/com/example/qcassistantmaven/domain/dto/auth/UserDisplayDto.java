package com.example.qcassistantmaven.domain.dto.auth;

public class UserDisplayDto {

    private Long id;

    private String username;

    public Long getId() {
        return id;
    }

    public UserDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDisplayDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
