package ru.skypro.homework.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.comment.FullCommentDto;

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
