package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.response.MoneyDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MoneyRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    public void insertIncome(String id, String amount, String detail) {
        String sql = "INSERT INTO money (id, amount, type, detail) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, amount, true, detail);
    }

    public void insertOutcome(String id, String amount, String detail) {
        String sql = "INSERT INTO money (id, amount, type, detail) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, amount, false, detail);
    }

    public List<MoneyDataResponse> getAllMoneyDataByUserId(String id) {
        String sql = "SELECT amount, type, detail, created_at FROM money WHERE id = ? ORDER BY created_at ASC";

        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> new MoneyDataResponse(
                rs.getString("amount"),
                rs.getBoolean("type"),
                rs.getString("detail"),
                rs.getTimestamp("created_at").toString()
        ));
    }

    public int getTodayTotalByUserId(String id, boolean type) {
        String sql = """
        SELECT COALESCE(SUM(CAST(amount AS SIGNED)), 0)
        FROM money
        WHERE id = ?
          AND type = ?
          AND DATE(created_at) = CURRENT_DATE
        """;

        return jdbcTemplate.queryForObject(sql, Integer.class, id, type);
    }
}

