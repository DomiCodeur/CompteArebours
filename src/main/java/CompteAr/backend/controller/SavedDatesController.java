package CompteAr.backend.controller;

import CompteAr.backend.model.SavedDates;
import CompteAr.backend.repository.SavedDatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dates")
public class SavedDatesController {
    @Autowired
    private SavedDatesRepository savedDatesRepository;
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<SavedDates> getAllSavedDates() {
        return savedDatesRepository.findAll();
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavedDates>> getSavedDatesByUserId(@PathVariable Integer userId) {
        List<SavedDates> savedDates = savedDatesRepository.findByUserId(userId);
        if (!savedDates.isEmpty()) {
            return new ResponseEntity<>(savedDates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{userId}")
    public ResponseEntity<SavedDates> createSavedDates(@PathVariable Integer userId, @RequestBody SavedDates savedDates) {
        savedDates.setUserId(userId);
        SavedDates savedDate = savedDatesRepository.save(savedDates);
        return new ResponseEntity<>(savedDate, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{userId}/{dateId}")
    public ResponseEntity<Void> deleteSavedDate(@PathVariable Integer userId, @PathVariable Integer dateId) {
        return savedDatesRepository.findByIdAndUserId(dateId, userId)
                .map(savedDates -> {
                    savedDatesRepository.delete(savedDates);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

