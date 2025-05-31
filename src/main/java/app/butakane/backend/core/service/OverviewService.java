package app.butakane.backend.core.service;

import app.butakane.backend.core.model.response.GoalResponse;
import app.butakane.backend.core.model.response.MoneyDataResponse;
import app.butakane.backend.core.model.response.OverviewResponse;
import app.butakane.backend.core.model.response.WalletDataResponse;
import app.butakane.backend.core.repository.GoalRepository;
import app.butakane.backend.core.repository.MoneyRepository;
import app.butakane.backend.core.repository.WalletRepository;
import app.butakane.backend.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class OverviewService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MoneyRepository moneyRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public OverviewResponse getOverview(String token) {
        String userId = jwtUtil.extractUserId(token);

        WalletDataResponse wallet = walletRepository.getWalletByUserId(userId);
        GoalResponse goal = goalRepository.getGoalByUserId(userId);
        List<MoneyDataResponse> moneyList = moneyRepository.getAllMoneyDataByUserId(userId);

        return new OverviewResponse(BigDecimal.valueOf(Long.parseLong(wallet.getBalance())), Collections.singletonList(goal), moneyList);
    }
}

