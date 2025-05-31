package app.butakane.backend.core.service;

import app.butakane.backend.core.model.request.DebtActionRequest;
import app.butakane.backend.core.model.request.DebtResolveRequest;
import app.butakane.backend.core.model.response.ApiResponse;
import app.butakane.backend.core.model.response.DebtDataResponse;
import app.butakane.backend.core.model.response.MyDebtOverviewResponse;
import app.butakane.backend.core.repository.BorrowRepository;
import app.butakane.backend.core.repository.DebtRepository;
import app.butakane.backend.core.repository.LendRepository;
import app.butakane.backend.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtService {

    @Autowired private DebtRepository debtRepository;
    @Autowired private BorrowRepository borrowRepository;
    @Autowired private LendRepository lendRepository;
    @Autowired private JwtUtil jwtUtil;

    public void recordBorrow(String token, DebtActionRequest request) {
        String id = jwtUtil.extractUserId(token);
        String amount = request.getAmount();

        if (amount == null || !amount.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid amount");
        }

        debtRepository.insertDebt(id, request.getName(), amount, request.getDetail(), false);

        int current = Integer.parseInt(borrowRepository.getBalance(id));
        int updated = current + Integer.parseInt(amount);
        borrowRepository.updateBalance(id, String.valueOf(updated));
    }

    public void recordLend(String token, DebtActionRequest request) {
        String id = jwtUtil.extractUserId(token);
        String amount = request.getAmount();

        if (amount == null || !amount.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid amount");
        }

        debtRepository.insertDebt(id, request.getName(), amount, request.getDetail(), true);

        int current = Integer.parseInt(lendRepository.getBalance(id));
        int updated = current + Integer.parseInt(amount);
        lendRepository.updateBalance(id, String.valueOf(updated));
    }

    public void payBack(String token, DebtResolveRequest request) {
        String userId = jwtUtil.extractUserId(token);
        String amount = request.getAmount();

        debtRepository.deleteDebtById(request.getId());

        int current = Integer.parseInt(borrowRepository.getBalance(userId));
        int updated = current - Integer.parseInt(amount);
        borrowRepository.updateBalance(userId, String.valueOf(updated));
    }

    public void receiveBack(String token, DebtResolveRequest request) {
        String userId = jwtUtil.extractUserId(token);
        String amount = request.getAmount();

        debtRepository.deleteDebtById(request.getId());

        int current = Integer.parseInt(lendRepository.getBalance(userId));
        int updated = current - Integer.parseInt(amount);
        lendRepository.updateBalance(userId, String.valueOf(updated));
    }

    public List<DebtDataResponse> getDebtData(String token) {
        String userId = jwtUtil.extractUserId(token);
        return debtRepository.getAllDebtsByUserId(userId);
    }

    public MyDebtOverviewResponse getMyDebtdata(String token) {
        String userId = jwtUtil.extractUserId(token);
        String borrowBal = borrowRepository.getBalance(userId);
        String lendBal = lendRepository.getBalance(userId);
        List<DebtDataResponse> debtList = debtRepository.getAllDebtsByUserId(userId);
        return new MyDebtOverviewResponse(lendBal, borrowBal, debtList);
    }

}


