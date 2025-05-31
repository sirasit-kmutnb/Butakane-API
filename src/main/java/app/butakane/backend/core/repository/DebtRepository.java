package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.response.DebtDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class DebtRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertDebt(String userId, String name, String amount, String detail, boolean type) {
        String sql = "INSERT INTO debt (debt_id, user_id, name, amount, detail, type) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), userId, name, amount, detail, type);
    }

    public Map<String, Object> getDebtById(String debtId) {
        String sql = "SELECT * FROM debt WHERE id = ?";
        return jdbcTemplate.queryForMap(sql, debtId);
    }

    public void deleteDebtById(String debtId) {
        String sql = "DELETE FROM debt WHERE debt_id = ?";
        jdbcTemplate.update(sql, debtId);
    }

    public List<DebtDataResponse> getAllDebtsByUserId(String userId) {
        String sql = "SELECT debt_id, name, amount, detail, type, created_at FROM debt WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> new DebtDataResponse(
                rs.getString("debt_id"),
                rs.getString("name"),
                rs.getString("amount"),
                rs.getString("detail"),
                rs.getBoolean("type"),
                rs.getTimestamp("created_at").toString()
        ));
    }


}

