package com.convive.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Por ahora, un servicio básico que acepta cualquier usuario
        // Deberías reemplazar esto con tu lógica real de usuarios desde la BD
        return new User(username, "", new ArrayList<>());
    }
}
