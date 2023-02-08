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

    @GetMapping("/{id}")
    public ResponseEntity<List<SavedDates>> getSavedDatesById(@PathVariable Integer id) {
        return savedDatesRepository.findById(id)
                .map(savedDates -> new ResponseEntity<>(Collections.singletonList(savedDates), HttpStatus.OK))
                .orElse(new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND));
    }


    @PostMapping("/{id}")
    public ResponseEntity<SavedDates> createSavedDates(@PathVariable Integer id, @RequestBody SavedDates savedDates) {
        savedDates.setUserId(id);
        SavedDates savedDate = savedDatesRepository.save(savedDates);
        return new ResponseEntity<>(savedDate, HttpStatus.CREATED);
    }


/*    @PutMapping("/{id}")
    public ResponseEntity<SavedDates> updateSavedDates(@PathVariable Integer id, @RequestBody SavedDates savedDatesDetails) {
        return savedDatesRepository.findByUserIdAndName(id, savedDatesDetails.getName())
                .map(savedDates -> {
                    savedDates.setDate(savedDatesDetails.getDate());
                    savedDates.setName(savedDatesDetails.getName());
                    savedDates.setTimeUnit(savedDatesDetails.getTimeUnit());
                    final SavedDates updatedSavedDates = savedDatesRepository.save(savedDates);
                    return new ResponseEntity<>(updatedSavedDates, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedDates(@PathVariable Integer id) {
        return savedDatesRepository.findById(id)
                .map(savedDates -> {
                    savedDatesRepository.delete(savedDates);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

