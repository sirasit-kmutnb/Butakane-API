package app.butakane.backend.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class OverviewResponse {
    private BigDecimal balance;
    private List<GoalResponse> goal_data;
    private List<MoneyDataResponse> in_out_list;
}
