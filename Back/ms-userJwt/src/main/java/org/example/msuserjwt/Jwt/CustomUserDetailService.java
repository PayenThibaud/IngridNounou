package org.example.msuserjwt.Jwt;

import org.example.msuserjwt.Repository.UserJwtRepository;
import org.example.msuserjwt.entity.UserJwt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service

public class CustomUserDetailService implements UserDetailsService {

    private final UserJwtRepository userAppRepository;

    public CustomUserDetailService(UserJwtRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserJwt> userAppOptional = userAppRepository.findByEmail(email);
        if (userAppOptional.isPresent()) {
            UserJwt userApp = userAppOptional.get();
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("user"));
            return new User(userApp.getEmail(), userApp.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}