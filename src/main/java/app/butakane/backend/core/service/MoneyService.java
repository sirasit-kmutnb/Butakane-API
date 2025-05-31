package app.butakane.backend.core.service;

import app.butakane.backend.core.model.request.IncomeRequest;
import app.butakane.backend.core.model.response.MoneyDataResponse;
import app.butakane.backend.core.model.response.MoneySummaryResponse;
import app.butakane.backend.core.repository.MoneyRepository;
import app.butakane.backend.core.repository.WalletRepository;
import app.butakane.backend.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MoneyService {

    @Autowired private MoneyRepository moneyRepository;
    @Autowired private WalletRepository walletRepository;
    @Autowired private JwtUtil jwtUtil;

    public void recordIncome(String token, IncomeRequest request) {
        String id = jwtUtil.extractUserId(token);
        String amount = request.getAmount();

        if (amount == null || !amount.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid amount");
        }

        // Insert into money table
        moneyRepository.insertIncome(id, amount, request.getDetail());

        // Update wallet balance
        int current = Integer.parseInt(walletRepository.getBalance(id));
        int updated = current + Integer.parseInt(amount);
        walletRepository.updateBalance(id, String.valueOf(updated));
    }

    public void recordOutcome(String token, IncomeRequest request) {
        String id = jwtUtil.extractUserId(token);
        String amount = request.getAmount();

        if (amount == null || !amount.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid amount");
        }

        // Insert into money table
        moneyRepository.insertOutcome(id, amount, request.getDetail());

        // Update wallet balance
        int current = Integer.parseInt(walletRepository.getBalance(id));
        int updated = current - Integer.parseInt(amount);
        walletRepository.updateBalance(id, String.valueOf(updated));
    }

    public List<MoneyDataResponse> getMoneyData(String token) {
        String id = jwtUtil.extractUserId(token);
        return moneyRepository.getAllMoneyDataByUserId(id);
    }

    public Integer getIncomeSummary(String token) {
        String id = jwtUtil.extractUserId(token);
        return moneyRepository.getTodayTotalByUserId(id, true);
    }

    public Integer getOutcomeSummary(String token) {
        String id = jwtUtil.extractUserId(token);
        return moneyRepository.getTodayTotalByUserId(id, false);
    }

}
