package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.response.WalletInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WalletRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<WalletInfoResponse> fetchWalletInfo(String userId) {
        String sql = "SELECT id, balance, updated_at FROM wallet WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", userId);

        return jdbcTemplate.query(sql, params, new RowMapper<WalletInfoResponse>() {
            @Override
            public WalletInfoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                WalletInfoResponse dto = new WalletInfoResponse();
                dto.setId(rs.getString("id"));
                dto.setBalance(rs.getInt("balance"));
                dto.setUpdatedAt(rs.getTimestamp("updated_at").toString());
                return dto;
            }
        });
    }

    public void insertMoney(String userId, int amount, boolean isIncome, String detail) {
        String sql = "INSERT INTO money (id, amount, type, detail) VALUES (:id, :amount, :type, :detail)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("amount", amount)
                .addValue("type", isIncome)
                .addValue("detail", detail);
        jdbcTemplate.update(sql, params);
    }

    public void updateWalletBalance(String userId, int delta) {
        String sql = "UPDATE wallet SET balance = balance + :amount WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("amount", delta);
        jdbcTemplate.update(sql, params);
    }
}
