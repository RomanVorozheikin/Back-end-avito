package ru.rvorozheikin.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rvorozheikin.homework.entity.Comment;

import java.util.List;

/**
 * @author rvorozheikin
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByAdPk(Integer adPk);
}

