package app.butakane.backend.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DebtResolveRequest {
    @JsonProperty("_id")
    private String id;      // debt record ID
    private String amount;
}

