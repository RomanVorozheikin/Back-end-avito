package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rvorozheikin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}
