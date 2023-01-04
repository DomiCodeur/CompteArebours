package CompteAr.backend.controller;

import CompteAr.backend.exception.ResourceNotFoundException;
import CompteAr.backend.model.SavedDates;
import CompteAr.backend.repository.SavedDatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dates")
public class SavedDatesController {
    @Autowired
    private SavedDatesRepository savedDatesRepository;

    @GetMapping("/getAllSavedDates")
    public List<SavedDates> getAllSavedDates() {
        return savedDatesRepository.findAll();
    }

    @GetMapping("/getSavedDates/{id}")
    public ResponseEntity<SavedDates> getSavedDatesById(@PathVariable(value = "id") Integer savedDatesId)
            throws ResourceNotFoundException {
        SavedDates savedDates = savedDatesRepository.findById(savedDatesId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedDates not found for this id :: " + savedDatesId));
        return ResponseEntity.ok().body(savedDates);
    }

    @PutMapping("/updateSavedDates/{id}")
    public ResponseEntity<SavedDates> updateSavedDates(@PathVariable(value = "id") Integer savedDatesId,
                                                       @RequestBody SavedDates savedDatesDetails) throws ResourceNotFoundException {
        SavedDates savedDates = savedDatesRepository.findById(savedDatesId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedDates not found for this id :: " + savedDatesId));

        savedDates.setDate(savedDatesDetails.getDate());
        savedDates.setName(savedDatesDetails.getName());
        final SavedDates updatedSavedDates = savedDatesRepository.save(savedDates);
        return ResponseEntity.ok(updatedSavedDates);
    }

    @DeleteMapping("/deleteSavedDates/{id}")
    public Map<String, Boolean> deleteSavedDates(@PathVariable(value = "id") Integer savedDatesId)
            throws ResourceNotFoundException {
        SavedDates savedDates = savedDatesRepository.findById(savedDatesId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedDates not found for this id :: " + savedDatesId));

        savedDatesRepository.delete(savedDates);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

