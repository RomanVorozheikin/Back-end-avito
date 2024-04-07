package ru.rvorozheikin.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rvorozheikin.homework.dto.user.RegisterDTO;

/**
 * @author rvorozheikin
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserService userService;


    /**
     * Login user by auth data
     * @param userName user's username (email)
     * @param password user's password
     * @return {@code true} if user with this {@code userName} is existed and
     * {@code password} is correct, <br>
     * {@code false} otherwise
     * @author rvorozheikin
     */
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }
    /**
     * Register new user
     * @param registerDTO object with new user's data
     * @return {@code true} if new user successfully registered, <br>
     * {@code false} if user with this username is already exist
     * @author rvorozheikin
     */
    public boolean register(RegisterDTO registerDTO) {
        if (userService.findUserByEmail(registerDTO.getUsername())==null) {
            String passwordEncode = encoder.encode(registerDTO.getPassword());
            userService.saveUserFromReg(registerDTO,passwordEncode);
            return true;
        }
        return false;
    }
}
