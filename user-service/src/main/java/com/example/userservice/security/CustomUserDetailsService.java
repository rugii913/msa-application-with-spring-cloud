package com.example.userservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/* (별도 작성) loadUserByUsername()과 loadUserByEmail()을 이어주는 역할을 하게 함 */
public interface CustomUserDetailsService extends UserDetailsService {

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.loadUserByEmail(username);
    }

    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
}
