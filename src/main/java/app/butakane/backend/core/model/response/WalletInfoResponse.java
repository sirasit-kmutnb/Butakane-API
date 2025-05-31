package app.butakane.backend.core.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WalletInfoResponse {
    private String id;
    private int balance;
    private String updatedAt;
}
