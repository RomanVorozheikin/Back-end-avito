package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.dto.user.RegisterDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UsersMapper;
import ru.skypro.homework.repository.UserRepository;

import javax.transaction.Transactional;

/**
 * @author rvorozheikin
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder encoder;
    private final ImageService imageService;
    /**
     * Set user's password
     * @param authentication target user's email (username)
     * @param newPasswordDTO object with new and current passwords
     * @return {@code true} if password successfully changed, <br>
     * {@code false} if current password is incorrect
     * @author rvorozheikin
     */
    public boolean setPassword(NewPassword newPasswordDTO, Authentication authentication) {
        User targetUser = userRepository.findByEmail(authentication.getName());
        if (encoder.matches(newPasswordDTO.getCurrentPassword(),targetUser.getEncodedPassword())) {
            targetUser.setEncodedPassword(encoder.encode(newPasswordDTO.getNewPassword()));
            save(targetUser);
            return true;
        }
        return false;
    }

    /**
     * Save user
     * @param user {@link User}
     * @author rvorozheikin
     */
    private void save(User user) {
        userRepository.save(user);
    }

    /**
     * Save user from {@link RegisterDTO} (register request)
     * @param registerDTO register request with new user's data
     * @param passwordEncode new user's encoded password
     * @author rvorozheikin
     */
    public void saveUserFromReg(RegisterDTO registerDTO, String passwordEncode) {
        userRepository.save(usersMapper.mapToUserFromRegisterDTO(registerDTO, passwordEncode));
    }

    /**
     * Return {@link UserDTO} of target user
     * @param email target user's email (username)
     * @return {@link UserDTO} of target user
     * @author rvorozheikin
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Getting info user
     * @param email email from user
     * @return {@link UserDTO} info wrapper user
     * @author rvorozheikin
     */
    public UserDTO getUserInfo(String email) {
        return usersMapper.mapToUserDTOFromUser(userRepository.findByEmail(email));
    }
    /**
     * Edit user
     * @param email target user's email (username)
     * @param userDTO {@link UserDTO} with user's new data
     * @return edited user in {@link UserDTO} instance
     * @author rvorozheikin
     */
    public UserDTO updateUserInfo(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email);
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setPhone(userDTO.getPhone());
        save(user);
        return usersMapper.mapToUserDTOFromUser(user);
    }

    /**
     * Upload user's avatar
     * @param targetEmail target user's email (username)
     * @param image {@link MultipartFile} with avatar
     * @author rvorozheikin
     */
    @Transactional
    public void uploadImage(MultipartFile image, String targetEmail) {
        User targetUser = findUserByEmail(targetEmail);
        targetUser.setImage(imageService.uploadUserAvatar(targetEmail, image));
        save(targetUser);
    }
}
