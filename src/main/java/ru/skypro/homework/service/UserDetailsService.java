package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User targetUser = userService.findUserByEmail(username);
        if (targetUser != null) {
            return buildUserDetails(targetUser);
        }
        throw new UsernameNotFoundException("Unknown user" + username);
    }

    private UserDetails buildUserDetails(User targetUser) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(targetUser.getEmail())
                .password(targetUser.getEncodedPassword())
                .roles(targetUser.getRole().name())
                .build();
    }
}
