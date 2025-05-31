package app.butakane.backend.core.service;

import app.butakane.backend.core.model.request.DebtActionRequest;
import app.butakane.backend.core.model.request.DebtResolveRequest;
import app.butakane.backend.core.model.response.ApiResponse;
import app.butakane.backend.core.repository.DebtRepository;
import app.butakane.backend.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DebtService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DebtRepository debtRepository;

    public ResponseEntity<?> getDebtData(HttpServletRequest request) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return ResponseEntity.ok(debtRepository.getDebtData(userId));
    }

    public ResponseEntity<?> getDebtSummary(HttpServletRequest request) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return ResponseEntity.ok(debtRepository.getDebtSummary(userId));
    }

    public ResponseEntity<ApiResponse> borrowMoney(HttpServletRequest request, DebtActionRequest body) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return debtRepository.createBorrowRecord(userId, body);
    }

    public ResponseEntity<ApiResponse> lendMoney(HttpServletRequest request, DebtActionRequest body) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return debtRepository.createLendRecord(userId, body);
    }

    public ResponseEntity<ApiResponse> payBackDebt(HttpServletRequest request, DebtResolveRequest body) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return debtRepository.resolveBorrowedDebt(userId, body);
    }

    public ResponseEntity<ApiResponse> receiveBackDebt(HttpServletRequest request, DebtResolveRequest body) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return debtRepository.resolveLentDebt(userId, body);
    }
}

