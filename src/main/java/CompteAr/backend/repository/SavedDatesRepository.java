package CompteAr.backend.repository;

import CompteAr.backend.model.SavedDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedDatesRepository extends JpaRepository<SavedDates, Integer> {

    SavedDates findByUserIdAndName(Integer userId, String name);

    List<SavedDates> findByUserId(Integer userId);

    void delete(SavedDates savedDates);

    Optional<SavedDates> findByIdAndUserId(Integer dateId, Integer userId);
}