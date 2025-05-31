package app.butakane.backend.core.model.request;

import lombok.Data;

@Data
public class IncomeRequest {
    private String amount;
    private String detail;
}
