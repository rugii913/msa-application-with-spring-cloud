package com.example.userservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/* (별도 작성) org.springframework.security.core.userdetails.User의 로직 그대로 사용, 다만 생성 시 username을 userId로 받도록 wrapping하는 역할 */
public class CustomUser extends User {

    public CustomUser(String userId, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getUserId() {
        return this.getUsername();
    }
}
