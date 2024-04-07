package ru.rvorozheikin.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rvorozheikin.homework.entity.User;

/**
 * @author rvorozheikin
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String name);
}
