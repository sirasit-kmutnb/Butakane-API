package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        return user;
    };

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource("username", username);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (id, username, password) VALUES (:id, :username, :password)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", UUID.randomUUID().toString())
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword());

        jdbcTemplate.update(sql, params);
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource("username", username);

        return jdbcTemplate.query(sql, params, rowMapper).stream().findFirst();
    }

    public void deleteById(String id) {
        String sql = "DELETE FROM users WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }
}