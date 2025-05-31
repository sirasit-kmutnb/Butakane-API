package app.butakane.backend.core.model.request;

import lombok.Data;

@Data
public class GoalRequest {
    private String item;
    private String price;
    private String url;
}
