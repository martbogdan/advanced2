package com.advanced.taracat.dao.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, BOT;


    @Override
    public String getAuthority() {
        return name();
    }
}
