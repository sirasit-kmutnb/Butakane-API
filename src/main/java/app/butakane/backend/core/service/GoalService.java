package app.butakane.backend.core.service;

import app.butakane.backend.core.model.request.GoalRequest;
import app.butakane.backend.core.model.request.PiggyRequest;
import app.butakane.backend.core.model.response.GoalResponse;
import app.butakane.backend.core.repository.GoalRepository;
import app.butakane.backend.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public List<GoalResponse> getGoal(String token) {
        String userId = jwtUtil.extractUserId(token);
        return Collections.singletonList(goalRepository.getGoalByUserId(userId));
    }

    public void saveGoal(String token, GoalRequest request) {
        String userId = jwtUtil.extractUserId(token);
        goalRepository.updateGoal(userId, request.getItem(), request.getPrice(), request.getUrl());
    }

    public void addPiggy(String token, PiggyRequest request) {
        String userId = jwtUtil.extractUserId(token);
        int current = Integer.parseInt(goalRepository.getPiggy(userId));
        int updated = current + Integer.parseInt(request.getAmount());
        goalRepository.updatePiggy(userId, String.valueOf(updated));
    }

    public void removePiggy(String token, PiggyRequest request) {
        String userId = jwtUtil.extractUserId(token);
        int current = Integer.parseInt(goalRepository.getPiggy(userId));
        int updated = current - Integer.parseInt(request.getAmount());
        goalRepository.updatePiggy(userId, String.valueOf(updated));
    }

    public void resetGoal(String token) {
        String userId = jwtUtil.extractUserId(token);
        goalRepository.resetGoal(userId);
    }
}

