package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;

@Component
public class UsersMapper {
    public UserDTO mapToUserDTOFromUser(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole(),
                user.getImage()
        );
    }

    public User mapToUserFromRegisterDTO(RegisterDTO registerDTO,String passwordEncode) {
        return new User(
                registerDTO.getUsername(),
                registerDTO.getFirstName(),
                registerDTO.getLastName(),
                registerDTO.getPhone(),
                registerDTO.getRole(),
                null,
                passwordEncode,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
