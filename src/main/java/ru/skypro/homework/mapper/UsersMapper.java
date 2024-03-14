package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

@Component
public class UsersMapper {
    @Value("${path.to.default.user.photo}")
    private String imageDir;

    public UserDTO mapToUserDTOFromUser(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole(),
                "/users/" + user.getUserId() + "/avatar"
        );
    }

    public User mapToUserFromRegisterDTO(RegisterDTO registerDTO,String passwordEncode) {
        User user = new User();
        user.setEmail(registerDTO.getUsername());
        user.setEncodedPassword(passwordEncode);
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPhone(registerDTO.getPhone());
        user.setRole(registerDTO.getRole());
        user.setImage(imageDir);
        return user;
    }
}
