package app.butakane.backend.core.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createUser(String id, String username, String hashedPassword) {
        String sql = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, username, hashedPassword);
    }

    public Map<String, Object> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForMap(sql, username);
    }

    public void deleteUserById(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
