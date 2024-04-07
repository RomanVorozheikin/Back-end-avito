package ru.rvorozheikin.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rvorozheikin.homework.dto.user.RegisterDTO;
import ru.rvorozheikin.homework.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author rvorozheikin
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserService userService;
    @Mock
    private UserDetailsService userDetailService;

    @InjectMocks
    private AuthService authService;

    @Test
    public void loginShouldReturnTrueIfUserSuccessfullyLogin() {
        UserDetails ud = org.springframework.security.core.userdetails.User.builder()
                .username("email")
                .password("pass")
                .roles("USER")
                .build();
        when(userDetailService.loadUserByUsername("email")).thenReturn(ud);
        when(encoder.matches(any(), any())).thenReturn(true);

        boolean result = authService.login("email", "pass");

        assertTrue(result);
    }

    @Test
    public void registerShouldReturnFalseIfUserAlreadyExist() {
        RegisterDTO req = new RegisterDTO();
        req.setUsername("email");

        when(userService.findUserByEmail(req.getUsername())).thenReturn(new User());

        boolean result = authService.register(req);

        assertFalse(result);
    }

    @Test
    public void registerShouldReturnTrueIfUserSuccessfullyRegistered() {
        RegisterDTO req = new RegisterDTO();
        req.setUsername("email");
        req.setPassword("pass");

        when(userService.findUserByEmail(req.getUsername())).thenReturn(null);
        when(encoder.encode(req.getPassword())).thenReturn("enPass");
        doNothing().when(userService).saveUserFromReg(req, "enPass");

        boolean result = authService.register(req);

        assertTrue(result);
    }
}