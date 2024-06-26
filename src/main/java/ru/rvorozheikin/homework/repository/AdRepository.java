package ru.rvorozheikin.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rvorozheikin.homework.entity.Ad;

/**
 * @author rvorozheikin
 */
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

}
