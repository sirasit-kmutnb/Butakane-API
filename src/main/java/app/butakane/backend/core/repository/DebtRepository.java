package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.request.DebtActionRequest;
import app.butakane.backend.core.model.request.DebtResolveRequest;
import app.butakane.backend.core.model.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DebtRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getDebtData(String userId) {
        String sql = "SELECT * FROM debt WHERE id = :id";
        return jdbcTemplate.queryForList(sql, new MapSqlParameterSource("id", userId));
    }

    public List<Map<String, Object>> getDebtSummary(String userId) {
        String sql = "SELECT * FROM lend l JOIN borrow b ON l.id = b.id WHERE l.id = :id";
        return jdbcTemplate.queryForList(sql, new MapSqlParameterSource("id", userId));
    }

    public ResponseEntity<ApiResponse> createBorrowRecord(String userId, DebtActionRequest request) {
        String name = request.getName();
        String amountStr = request.getAmount();
        String detail = request.getDetail() != null ? request.getDetail() : "";

        if (name == null || name.isBlank() || amountStr == null || !amountStr.matches("\\d+(\\.\\d+)?")) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid input"));
        }

        int amount = Integer.parseInt(amountStr);
        String insertDebt = "INSERT INTO debt (id, name, amount, detail, type) VALUES (:id, :name, :amount, :detail, FALSE)";
        jdbcTemplate.update(insertDebt, new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("name", name)
                .addValue("amount", amount)
                .addValue("detail", detail));

        String updateBorrow = "UPDATE borrow SET balance = balance + :amount WHERE id = :id";
        jdbcTemplate.update(updateBorrow, new MapSqlParameterSource("id", userId).addValue("amount", amount));

        return ResponseEntity.ok(new ApiResponse("Borrow record added"));
    }

    public ResponseEntity<ApiResponse> createLendRecord(String userId, DebtActionRequest request) {
        String name = request.getName();
        String amountStr = request.getAmount();
        String detail = request.getDetail() != null ? request.getDetail() : "";

        if (name == null || name.isBlank() || amountStr == null || !amountStr.matches("\\d+(\\.\\d+)?")) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid input"));
        }

        int amount = Integer.parseInt(amountStr);
        String insertDebt = "INSERT INTO debt (id, name, amount, detail, type) VALUES (:id, :name, :amount, :detail, TRUE)";
        jdbcTemplate.update(insertDebt, new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("name", name)
                .addValue("amount", amount)
                .addValue("detail", detail));

        String updateLend = "UPDATE lend SET balance = balance + :amount WHERE id = :id";
        jdbcTemplate.update(updateLend, new MapSqlParameterSource("id", userId).addValue("amount", amount));

        return ResponseEntity.ok(new ApiResponse("Lend record added"));
    }

    public ResponseEntity<ApiResponse> resolveBorrowedDebt(String userId, DebtResolveRequest request) {
        String debtId = request.getId();
        int amount = request.getAmount();

        String deleteSql = "DELETE FROM debt WHERE id = :id AND _id = :_id";
        jdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("_id", debtId));

        String updateBorrow = "UPDATE borrow SET balance = balance - :amount WHERE id = :id";
        jdbcTemplate.update(updateBorrow, new MapSqlParameterSource("id", userId).addValue("amount", amount));

        return ResponseEntity.ok(new ApiResponse("Borrowed debt paid back"));
    }

    public ResponseEntity<ApiResponse> resolveLentDebt(String userId, DebtResolveRequest request) {
        String debtId = request.getId();
        int amount = request.getAmount();

        String deleteSql = "DELETE FROM debt WHERE id = :id AND _id = :_id";
        jdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("_id", debtId));

        String updateLend = "UPDATE lend SET balance = balance - :amount WHERE id = :id";
        jdbcTemplate.update(updateLend, new MapSqlParameterSource("id", userId).addValue("amount", amount));

        return ResponseEntity.ok(new ApiResponse("Lent debt received back"));
    }
}
