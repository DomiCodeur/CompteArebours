package CompteAr.backend.controller;


import CompteAr.backend.model.SavedDates;
import CompteAr.backend.repository.SavedDatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dates")
public class SavedDatesController {
    @Autowired
    private SavedDatesRepository savedDatesRepository;

    @GetMapping("/getAllSavedDates")
    public Iterable<SavedDates> getAllSavedDates() {
        return savedDatesRepository.findAll();
    }
    @PostMapping("/setSavedDates")
    public SavedDates setSavedDates(@RequestBody SavedDates savedDates) {
        return savedDatesRepository.save(savedDates);
    }

    @GetMapping("/getSavedDates")
    public SavedDates getSavedDatesByUserId(@RequestParam long userId) {
        return savedDatesRepository.findByUserId(userId);
    }

    @DeleteMapping("/deleteSavedDates")
    public void deleteSavedDates(@RequestBody SavedDates savedDates) {
        savedDatesRepository.delete(savedDates);
    }

    @PutMapping("/updateSavedDates")
    public void updateSavedDates(@RequestBody SavedDates savedDates) {
        savedDatesRepository.save(savedDates);
    }




}
