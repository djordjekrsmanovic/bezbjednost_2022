package com.ftn.security.authentication;

import com.ftn.security.model.ApplicationRole;
import com.ftn.security.model.ApplicationUser;
import com.ftn.security.service.UserService;
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
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser user = userService.getUserByUserName(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(email, user.getPassword(), getRole(user.getApplicationRole()));
    }

    private List<SimpleGrantedAuthority> getRole(ApplicationRole role) {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        String roleName = ROLE_PREFIX + role;
        grantedAuthorities.add(new SimpleGrantedAuthority(roleName));
        return grantedAuthorities;
    }

}
