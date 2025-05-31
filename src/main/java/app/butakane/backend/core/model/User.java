package app.butakane.backend.core.model;

import lombok.Data;

import java.time.Instant;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private Instant createdAt;
}
