package ru.rvorozheikin.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.rvorozheikin.homework.service.AuthService;
import ru.rvorozheikin.homework.dto.user.LoginDTO;
import ru.rvorozheikin.homework.dto.user.RegisterDTO;

/**
 * @author rvorozheikin
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Authorize user
     * @param loginDTO {@link LoginDTO} with auth data
     * @return {@link HttpStatus#OK} if auth successful, <br>
     * {@link HttpStatus#UNAUTHORIZED} otherwise
     * @author rvovrozeikin
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        if (authService.login(loginDTO.getUsername(), loginDTO.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    /**
     * Register new user
     * @param registerDTO {@link RegisterDTO} with registration data
     * @return {@link HttpStatus#CREATED} if registration successful, <br>
     * {@link HttpStatus#BAD_REQUEST} otherwise
     * @author rvovrozeikin
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (authService.register(registerDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
