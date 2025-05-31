package app.butakane.backend.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MoneyDataResponse {
    private int amount;
    private boolean type; // true = income, false = outcome
    private String detail;
    private LocalDateTime createdAt;
}
