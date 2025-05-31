package app.butakane.backend.core.service;

import app.butakane.backend.core.model.response.MoneyDataResponse;
import app.butakane.backend.core.repository.MoneyRepository;
import app.butakane.backend.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyService {

    @Autowired
    private MoneyRepository moneyRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<List<MoneyDataResponse>> getMoneyData(HttpServletRequest request) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return ResponseEntity.ok(moneyRepository.getMoneyHistory(userId));
    }

    public ResponseEntity<Integer> getIncomeSummary(HttpServletRequest request) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return ResponseEntity.ok(moneyRepository.getTodaySum(userId, true));
    }

    public ResponseEntity<Integer> getOutcomeSummary(HttpServletRequest request) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return ResponseEntity.ok(moneyRepository.getTodaySum(userId, false));
    }
}