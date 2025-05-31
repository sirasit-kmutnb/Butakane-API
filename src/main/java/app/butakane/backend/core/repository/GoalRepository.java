package app.butakane.backend.core.repository;

import app.butakane.backend.core.model.response.GoalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GoalRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GoalResponse getGoalByUserId(String id) {
        String sql = "SELECT item, price, piggy, url FROM goal WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new GoalResponse(
                rs.getString("item"),
                rs.getString("price"),
                rs.getString("piggy"),
                rs.getString("url")
        ));
    }

    public void updateGoal(String id, String item, String price, String url) {
        String sql = "UPDATE goal SET item = ?, price = ?, url = ? WHERE id = ?";
        jdbcTemplate.update(sql, item, price, url, id);
    }

    public void createBase(String id) {
        String sql = "INSERT INTO goal (id, item, price, piggy, url) VALUES (?, '', '0', '0', '')";
        jdbcTemplate.update(sql, id);
    }

    public String getPiggy(String id) {
        String sql = "SELECT piggy FROM goal WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public void updatePiggy(String id, String piggy) {
        String sql = "UPDATE goal SET piggy = ? WHERE id = ?";
        jdbcTemplate.update(sql, piggy, id);
    }

    public void resetGoal(String id) {
        String sql = "UPDATE goal SET item = '', price = '0', piggy = '0', url = '' WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
