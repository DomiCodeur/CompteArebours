package CompteAr.backend.repository;

import CompteAr.backend.model.SavedDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedDatesRepository extends JpaRepository<SavedDates, Integer> {

        SavedDates findByUserId(Integer userId);


}