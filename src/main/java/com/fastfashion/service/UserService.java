package com.fastfashion.service;

import com.fastfashion.domain.User;
import com.fastfashion.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User register(String email, String password, String name, String phone) {
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("Email already registered");
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(password));
        u.setName(name);
        u.setPhone(phone);
        return userRepository.save(u);
    }

    public boolean verifyPassword(String raw, String hash) {
        return encoder.matches(raw, hash);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(username);
        if (u == null) throw new UsernameNotFoundException("User not found");
        UserBuilder b = org.springframework.security.core.userdetails.User.withUsername(u.getEmail());
        b.password(u.getPasswordHash());
        b.roles("USER");
        return b.build();
    }
}
