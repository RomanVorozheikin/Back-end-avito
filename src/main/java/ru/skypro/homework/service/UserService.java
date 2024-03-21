package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UsersMapper;
import ru.skypro.homework.repository.UserRepository;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder encoder;
    private final ImageService imageService;
    public boolean setPassword(NewPassword newPasswordDTO, Authentication authentication) {
        User targetUser = userRepository.findByEmail(authentication.getName());
        if (encoder.matches(newPasswordDTO.getCurrentPassword(),targetUser.getEncodedPassword())) {
            targetUser.setEncodedPassword(encoder.encode(newPasswordDTO.getNewPassword()));
            save(targetUser);
            return true;
        }
        return false;
    }

    private void save(User user) {
        userRepository.save(user);
    }

    public void saveUserFromReg(RegisterDTO registerDTO, String passwordEncode) {
        userRepository.save(usersMapper.mapToUserFromRegisterDTO(registerDTO, passwordEncode));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDTO getUserInfo(String name) {
        return usersMapper.mapToUserDTOFromUser(userRepository.findByEmail(name));
    }
    public UserDTO updateUserInfo(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email);
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setPhone(userDTO.getPhone());
        save(user);
        return usersMapper.mapToUserDTOFromUser(user);
    }

    @Transactional
    public void uploadImage(MultipartFile image, String targetEmail) {
        User targetUser = findUserByEmail(targetEmail);
        targetUser.setImage(imageService.uploadUserAvatar(targetEmail, image));
        save(targetUser);
    }
}
