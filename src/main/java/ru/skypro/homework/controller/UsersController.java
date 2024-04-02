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
import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.service.UserService;

/**
 * @author rvorozheikin
 */
@Slf4j
@Service
@RequestMapping(value = "/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    /**
     * Set password of target user
     * @param authentication user auth data
     * @param newPassword object with new and current passwords
     * @return {@link HttpStatus#OK} if password successfully set, <br>
     * {@link HttpStatus#BAD_REQUEST} otherwise
     * @author rvorozheikin
     */
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword,
                                         Authentication authentication) {
        if (userService.setPassword(newPassword, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    /**
     * Return user's data
     * @param authentication user auth data
     * @return {@link HttpStatus#OK} with user's data in {@link UserDTO} instance
     * @author rvorozheikin
     */
    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInfo(authentication.getName()));
    }
    /**
     * Edit user's data
     * @param authentication user auth data
     * @param userDTO object with new user's data
     * @return {@link HttpStatus#OK} with new user's data in {@link UserDTO} instance
     * @author rvorozheikin
     */
    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserDTO userDTO,
                                                     Authentication authentication) {
        return ResponseEntity.ok(userService.updateUserInfo(authentication.getName(),userDTO));
    }
    /**
     * Edit user's avatar
     * @param authentication user auth data
     * @param image {@link MultipartFile} with new avatar
     * @return {@link HttpStatus#OK} when user's avatar edited
     * @author rvorozheikin
     */
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatarUser(@RequestPart("image") MultipartFile image,
                                                   Authentication authentication) {
        userService.uploadImage(image, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
