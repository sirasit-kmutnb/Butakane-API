package app.butakane.backend.core.service;

import app.butakane.backend.core.model.request.IncomeRequest;
import app.butakane.backend.core.model.request.OutcomeRequest;
import app.butakane.backend.core.model.response.ApiResponse;
import app.butakane.backend.core.model.response.WalletDataResponse;
import app.butakane.backend.core.repository.WalletRepository;
import app.butakane.backend.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired private WalletRepository walletRepository;
    @Autowired private JwtUtil jwtUtil;

    public WalletDataResponse getWalletInfo(String token) {
        String userId = jwtUtil.extractUserId(token);
        return walletRepository.getWalletByUserId(userId);
    }
}

