package app.butakane.backend.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebtDataResponse {
    @JsonProperty("_id")
    private String debtId;
    private String name;
    private String amount;
    private String detail;
    private boolean type;
    private String createdAt;
}

