package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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