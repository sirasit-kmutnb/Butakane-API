package app.butakane.backend.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Data
@AllArgsConstructor
public class MyDebtOverviewResponse {
    private List<Map<String, String>> borrow_balance;
    private String balance;
    private List<DebtDataResponse> debt_list;

    public MyDebtOverviewResponse(String lendBalance, String borrowBalance, List<DebtDataResponse> debts) {
        this.borrow_balance = List.of(Map.of("balance", borrowBalance));
        this.balance = lendBalance;
        this.debt_list = debts;
    }

}

