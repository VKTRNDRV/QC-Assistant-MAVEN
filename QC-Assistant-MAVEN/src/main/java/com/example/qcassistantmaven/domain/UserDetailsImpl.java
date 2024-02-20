package com.example.qcassistantmaven.domain;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsImpl extends User{
    public UserDetailsImpl(String username,
                           String password,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
