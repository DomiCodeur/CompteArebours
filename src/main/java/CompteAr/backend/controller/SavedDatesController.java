package CompteAr.backend.controller;

import CompteAr.backend.model.SavedDates;
import CompteAr.backend.repository.SavedDatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dates")
public class SavedDatesController {
    @Autowired
    private SavedDatesRepository savedDatesRepository;

    @GetMapping
    public List<SavedDates> getAllSavedDates() {
        return savedDatesRepository.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavedDates>> getSavedDatesByUserId(@PathVariable Integer userId) {
        List<SavedDates> savedDates = savedDatesRepository.findByUserId(userId);
        if (!savedDates.isEmpty()) {
            return new ResponseEntity<>(savedDates, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SavedDates> createSavedDates(@PathVariable Integer userId, @RequestBody SavedDates savedDates) {
        savedDates.setUserId(userId);
        SavedDates savedDate = savedDatesRepository.save(savedDates);
        return new ResponseEntity<>(savedDate, HttpStatus.CREATED);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteSavedDates(@PathVariable Integer userId) {
        return savedDatesRepository.findById(userId)
                .map(savedDates -> {
                    savedDatesRepository.delete(savedDates);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

