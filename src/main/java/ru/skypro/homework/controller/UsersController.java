package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.UserService;

@Slf4j
@Service
@RequestMapping(value = "/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword,
                                         Authentication authentication) {
        if (userService.setPassword(newPassword, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInfo(authentication.getName()));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserDTO userDTO,
                                                     Authentication authentication) {
        return ResponseEntity.ok(userService.updateUserInfo(authentication.getName(),userDTO));
    }

    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatarUser(@RequestPart("image") MultipartFile image,
                                                   Authentication authentication) {
        userService.uploadImage(image, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
