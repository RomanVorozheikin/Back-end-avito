package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rvorozheikin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdDto {
    private String title;
    private Integer price;
    private String description;
}
