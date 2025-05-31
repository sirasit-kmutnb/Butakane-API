package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.response.WalletDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public WalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public WalletDataResponse getWalletByUserId(String id) {
        String sql = "SELECT id, balance FROM wallet WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> new WalletDataResponse(
                        rs.getString("id"),
                        rs.getString("balance")
                )
        );
    }

    public void createBase(String id) {
        String sql = "INSERT INTO wallet (id, balance) VALUES (?, '0')";
        jdbcTemplate.update(sql, id);
    }

    public void updateBalance(String id, String newBalance) {
        String sql = "UPDATE wallet SET balance = ? WHERE id = ?";
        jdbcTemplate.update(sql, newBalance, id);
    }

    public String getBalance(String id) {
        String sql = "SELECT balance FROM wallet WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

}

