package ru.bahusdivus.meet5.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.bahusdivus.meet5.entities.UserEntity;
import ru.bahusdivus.meet5.entities.VisitEntity;

import java.util.List;
import java.util.Optional;

@Service
public class DbService {
    private final JdbcTemplate jdbcTemplate;

    public DbService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserEntity> getUserById(int id) {
        String sql = "SELECT id, user_name, b_date, reg_date, note FROM meet5_user WHERE id = ?";
        return jdbcTemplate.query(sql, UserEntity::of, id);
    }

    public List<VisitEntity> getVisitsByVisitorId(int id) {
        String sql = "SELECT v_time, user_id, profile_id, metadata FROM meet5_visit\n" +
                "WHERE user_id = ? ORDER BY v_time";
        return jdbcTemplate.query(sql, VisitEntity::listFrom, id);
    }

    public boolean isUserFraudulent(int id) {
        // don't want to deal with named JdbcTemplate
        String sql = "SELECT count(distinct profile_id) FROM meet5_visit\n" +
                "WHERE user_id = ? AND v_time\n" +
                "BETWEEN (SELECT reg_date FROM meet5_user WHERE id = ?)\n" +
                "AND (SELECT reg_date + interval '10 minutes' FROM meet5_user WHERE id = ?)";
        Integer visitsCount = jdbcTemplate.queryForObject(sql, Integer.class, id, id, id);
        return visitsCount != null && visitsCount > 100;
    }

    @SuppressWarnings("DataFlowIssue")
    public int getUserSequence() {
        return jdbcTemplate.queryForObject("SELECT nextval('meet5_user_id_seq')", Integer.class);
    }

    public void saveUser(UserEntity user) {
        String sql = "INSERT INTO meet5_user (id, user_name, b_date, reg_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getBirthdate(), user.getRegDate());
    }

    public void saveVisit(VisitEntity visit) {
        String sql = "INSERT INTO meet5_visit (user_id, profile_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, visit.getVisitor(), visit.getProfile());
    }
}
