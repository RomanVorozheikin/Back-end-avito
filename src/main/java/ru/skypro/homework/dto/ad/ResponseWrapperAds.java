package ru.skypro.homework.dto.ad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.ad.AdDto;

import java.util.List;

/**
 * @author rvorozheikin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapperAds {
    private Integer count;
    private List<AdDto> results;
}
