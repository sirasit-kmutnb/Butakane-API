package app.butakane.backend.core.service;

import app.butakane.backend.core.model.request.IncomeRequest;
import app.butakane.backend.core.model.request.OutcomeRequest;
import app.butakane.backend.core.model.response.ApiResponse;
import app.butakane.backend.core.model.response.WalletInfoResponse;
import app.butakane.backend.core.repository.WalletRepository;
import app.butakane.backend.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<List<WalletInfoResponse>> getWalletInfo(HttpServletRequest request) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        return ResponseEntity.ok(walletRepository.fetchWalletInfo(userId));
    }

    public ResponseEntity<ApiResponse> addIncome(HttpServletRequest request, IncomeRequest body) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        String amountStr = body.getAmount();
        String detail = body.getDetail() != null ? body.getDetail() : "";

        if (amountStr == null || !amountStr.matches("\\d+(\\.\\d+)?")) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid amount input"));
        }

        int amount = Integer.parseInt(amountStr);
        walletRepository.insertMoney(userId, amount, true, detail);
        walletRepository.updateWalletBalance(userId, amount);
        return ResponseEntity.ok(new ApiResponse("Income recorded"));
    }

    public ResponseEntity<ApiResponse> addOutcome(HttpServletRequest request, OutcomeRequest body) {
        String userId = jwtUtil.extractUserId(request.getHeader("Authorization"));
        String amountStr = body.getAmount();
        String detail = body.getDetail() != null ? body.getDetail() : "";

        if (amountStr == null || !amountStr.matches("\\d+(\\.\\d+)?")) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid amount input"));
        }

        int amount = Integer.parseInt(amountStr);
        walletRepository.insertMoney(userId, amount, false, detail);
        walletRepository.updateWalletBalance(userId, -amount);
        return ResponseEntity.ok(new ApiResponse("Outcome recorded"));
    }
}
