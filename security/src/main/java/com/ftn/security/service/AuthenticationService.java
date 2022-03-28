package com.ftn.security.service;

import com.ftn.security.authentication.JwtUtil;
import com.ftn.security.authentication.model.AuthenticationRequest;
import com.ftn.security.authentication.model.AuthenticationResponse;
import com.ftn.security.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final ClientService clientService;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(), authenticationRequest.getPassword());
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password.", e);
        }

        Client client = clientService.getClientByMail(authenticationRequest.getMail());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getMail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwt,client.getMail(),client.getRole());

    }
}
