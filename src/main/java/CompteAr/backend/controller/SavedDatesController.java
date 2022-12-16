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
    @GetMapping("/getSavedDates/{userId}")
    public SavedDates getSavedDatesByUserId(@PathVariable long userId) {
        return savedDatesRepository.findByUserId(userId);
    }


}
