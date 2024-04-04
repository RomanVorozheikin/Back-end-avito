package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.user.LoginDTO;
import ru.skypro.homework.dto.user.RegisterDTO;
import ru.skypro.homework.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author rvorozheikin
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private AuthService authService;


    @InjectMocks
    private AuthController authController;

    @Test
    public void testLoginStatusOK() {
        LoginDTO loginDTO = new LoginDTO();

        when(authService.login(loginDTO.getUsername(), loginDTO.getPassword())).thenReturn(true);

        ResponseEntity<?> response = authController.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRegisterStatusOK() {
        RegisterDTO registerDTO = new RegisterDTO();

        when(authService.register(registerDTO)).thenReturn(true);

        ResponseEntity<?> response = authController.register(registerDTO);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }
}