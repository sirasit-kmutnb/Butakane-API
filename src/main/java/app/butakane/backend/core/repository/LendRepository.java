package app.butakane.backend.core.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LendRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getBalance(String id) {
        String sql = "SELECT balance FROM lend WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public void updateBalance(String id, String balance) {
        String sql = "UPDATE lend SET balance = ? WHERE id = ?";
        jdbcTemplate.update(sql, balance, id);
    }

    public void createBase(String id) {
        String sql = "INSERT INTO lend (id, balance) VALUES (?, '0')";
        jdbcTemplate.update(sql, id);
    }
}

