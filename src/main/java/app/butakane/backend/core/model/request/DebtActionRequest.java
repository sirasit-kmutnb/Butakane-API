package app.butakane.backend.core.model.request;

import lombok.Data;

@Data
public class DebtActionRequest {
    private String name;
    private String amount;
    private String detail;
}

