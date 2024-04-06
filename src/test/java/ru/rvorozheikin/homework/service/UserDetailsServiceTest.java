package ru.rvorozheikin.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.rvorozheikin.homework.dto.Role;
import ru.rvorozheikin.homework.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDetailsService userDetailService;

    @Test
    public void loadUserByUserNameShouldThrowExceptionWhenUserNotExisted() {
        when(userService.findUserByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailService.loadUserByUsername("email"));
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetails() {
        User testUser = new User();
        testUser.setEmail("email");
        testUser.setEncodedPassword("enPass");
        testUser.setRole(Role.USER);

        when(userService.findUserByEmail(testUser.getEmail())).thenReturn(testUser);

        UserDetails result = userDetailService.loadUserByUsername(testUser.getEmail());

        assertInstanceOf(UserDetails.class, result);
    }
}