package ru.rvorozheikin.homework.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author rvorozheikin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapperComment {
    private Integer count;
    private List<FullCommentDto> results;
}
