package ru.skypro.homework.dto.user;

import lombok.Data;

/**
 * @author rvorozheikin
 */
@Data
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
