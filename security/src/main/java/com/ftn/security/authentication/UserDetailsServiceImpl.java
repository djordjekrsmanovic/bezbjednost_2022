package com.ftn.security.authentication;

import com.ftn.security.model.Role;
import com.ftn.security.model.Client;
import com.ftn.security.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";
    private final ClientService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client user = userService.getClientByMail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(email, user.getPassword(), user.getAuthorities());
    }

    private List<SimpleGrantedAuthority> getRole(Role role) {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        String roleName = ROLE_PREFIX + role.getName();
        grantedAuthorities.add(new SimpleGrantedAuthority(roleName));
        return grantedAuthorities;
    }

}
