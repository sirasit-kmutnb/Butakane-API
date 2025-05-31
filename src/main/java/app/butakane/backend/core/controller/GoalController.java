package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.request.GoalRequest;
import app.butakane.backend.core.model.request.PiggyRequest;
import app.butakane.backend.core.model.response.GoalResponse;
import app.butakane.backend.core.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/goalinfo")
    public ResponseEntity<List<GoalResponse>> getGoal(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(goalService.getGoal(token));
    }

    @PutMapping("/savegoal")
    public ResponseEntity<String> saveGoal(@RequestHeader("Authorization") String token,
                                           @RequestBody GoalRequest request) {
        goalService.saveGoal(token, request);
        return ResponseEntity.ok("Goal saved.");
    }

    @PutMapping("/addpiggy")
    public ResponseEntity<String> addPiggy(@RequestHeader("Authorization") String token,
                                           @RequestBody PiggyRequest request) {
        goalService.addPiggy(token, request);
        return ResponseEntity.ok("Piggy updated.");
    }

    @PutMapping("/removepiggy")
    public ResponseEntity<String> removePiggy(@RequestHeader("Authorization") String token,
                                              @RequestBody PiggyRequest request) {
        goalService.removePiggy(token, request);
        return ResponseEntity.ok("Piggy updated.");
    }

    @PutMapping("/reachedgoal")
    public ResponseEntity<String> resetGoal(@RequestHeader("Authorization") String token) {
        goalService.resetGoal(token);
        return ResponseEntity.ok("Goal reset.");
    }
}
