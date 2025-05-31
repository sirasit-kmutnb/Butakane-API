package app.butakane.backend.core.controller;

import com.example.financeapp.service.GoalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/info")
    public ResponseEntity<?> getGoalData(HttpServletRequest request) {
        return goalService.getGoalData(request);
    }

    @PutMapping("/save")
    public ResponseEntity<?> saveGoal(HttpServletRequest request, @RequestBody String payload) {
        return goalService.saveGoal(request, payload);
    }

    @PutMapping("/add-piggy")
    public ResponseEntity<?> addPiggy(HttpServletRequest request, @RequestBody String payload) {
        return goalService.addPiggy(request, payload);
    }

    @PutMapping("/remove-piggy")
    public ResponseEntity<?> removePiggy(HttpServletRequest request, @RequestBody String payload) {
        return goalService.removePiggy(request, payload);
    }

    @PutMapping("/reached")
    public ResponseEntity<?> reachedGoal(HttpServletRequest request) {
        return goalService.reachedGoal(request);
    }
}