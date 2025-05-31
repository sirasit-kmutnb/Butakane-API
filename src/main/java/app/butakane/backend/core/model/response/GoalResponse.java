package app.butakane.backend.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalResponse {
    private String item;
    private String price;
    private String piggy;
    private String url;
}
