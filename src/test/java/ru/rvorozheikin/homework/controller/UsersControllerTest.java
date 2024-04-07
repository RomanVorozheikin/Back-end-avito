package ru.rvorozheikin.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.rvorozheikin.homework.service.UserService;
import ru.rvorozheikin.homework.dto.user.UserDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author rvorozheikin
 */
@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private UsersController userController;

    @Test
    public void getMeShouldReturnStatusOkAndCallMethodGetUserDtoByEmail() {
        String testEmail = "email";
        when(authentication.getName()).thenReturn(testEmail);
        when(userService.getUserInfo(testEmail)).thenReturn(new UserDTO());

        ResponseEntity<UserDTO> response = userController.getUserInfo(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(UserDTO.class, response.getBody());
    }

    @Test
    public void setPasswordShouldReturnBadRequestWhenServiceReturnFalse() {
        when(userService.setPassword(any(), any())).thenReturn(false);

        ResponseEntity<?> response = userController.setPassword(null, authentication);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void setPasswordShouldReturnOkWhenServiceReturnTrue() {
        when(userService.setPassword(any(), any())).thenReturn(true);

        ResponseEntity<?> response = userController.setPassword(null, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void editUserShouldReturnOkWithUserDtoAndCallOnlyEditUserMethod() {
        when(authentication.getName()).thenReturn("");
        when(userService.updateUserInfo(anyString(), any())).thenReturn(new UserDTO());

        ResponseEntity<UserDTO> response = userController.updateUserInfo(null, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(UserDTO.class, response.getBody());
        verify(userService, only()).updateUserInfo(anyString(), any());
    }

    @Test
    public void editImageShouldCallMethodUploadImageAndReturnOk() {
        when(authentication.getName()).thenReturn("");
        doNothing().when(userService).uploadImage(any(),anyString());

        ResponseEntity<?> response = userController.updateAvatarUser(null,authentication );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, only()).uploadImage(any(),anyString());
    }
}