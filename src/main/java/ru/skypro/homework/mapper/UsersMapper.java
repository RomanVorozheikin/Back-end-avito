package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.user.RegisterDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;

/**
 * @author rvorozheikin
 */
@Component
public class UsersMapper {
    /**
     * Map {@link User} to {@link UserDTO}
     * @param user target {@link User}
     * @return created {@link UserDTO}
     */
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
    /**
     * Create new {@link User} from register request
     * @param registerDTO register request
     * @param passwordEncode user's encoded password
     * @return created {@link User}
     */
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
