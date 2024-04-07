package ru.rvorozheikin.homework.dto.ad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rvorozheikin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdDto {
    private Integer pk;
    private String image;
    private Integer price;
    private String title;
    private Integer author;
}
