package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.response.MoneyDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public class MoneyRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<MoneyDataResponse> getMoneyHistory(String userId) {
        String sql = "SELECT amount, type, detail, created_at FROM money WHERE id = :id";
        var params = new MapSqlParameterSource("id", userId);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> new MoneyDataResponse(
                rs.getInt("amount"),
                rs.getBoolean("type"),
                rs.getString("detail"),
                rs.getTimestamp("created_at").toLocalDateTime()
        ));
    }

    public int getTodaySum(String userId, boolean isIncome) {
        String sql = "SELECT amount, created_at FROM money WHERE id = :id AND type = :type";
        var params = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("type", isIncome);

        LocalDate today = LocalDate.now();

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Timestamp created = rs.getTimestamp("created_at");
            if (created.toLocalDateTime().toLocalDate().equals(today)) {
                return rs.getInt("amount");
            } else {
                return 0;
            }
        }).stream().mapToInt(Integer::intValue).sum();
    }
}
