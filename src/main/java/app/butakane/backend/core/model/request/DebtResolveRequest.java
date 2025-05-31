package app.butakane.backend.core.model.request;

import lombok.Data;

@Data
public class DebtResolveRequest {
    private String id;      // debt record ID
    private int amount;
}

