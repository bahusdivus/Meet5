package ru.bahusdivus.meet5.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import ru.bahusdivus.meet5.Meet5ApplicationTests;
import ru.bahusdivus.meet5.entities.UserEntity;
import ru.bahusdivus.meet5.entities.VisitEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class DbServiceTest extends Meet5ApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DbService dbService;

    @Test
    void getUserById() {
        jdbcTemplate.execute("INSERT INTO meet5_user (id, user_name, b_date, reg_date) " +
                                     "VALUES (1, 'test', now(), now())");

        Optional<UserEntity> user1 = dbService.getUserById(1);
        assertTrue(user1.isPresent());
        assertEquals(1, user1.get().getId());
        assertEquals("test", user1.get().getName());

        Optional<UserEntity> user2 = dbService.getUserById(2);
        assertTrue(user2.isEmpty());
    }

    @Test
    void getVisitsByVisitorId() {
        jdbcTemplate.execute("INSERT INTO meet5_user (id, user_name, b_date, reg_date) " +
                                     "VALUES (1, 'test', now(), now())");
        jdbcTemplate.execute("INSERT INTO meet5_visit (user_id, profile_id) VALUES (1, 1)");
        jdbcTemplate.execute("INSERT INTO meet5_visit (user_id, profile_id) VALUES (1, 1)");

        List<VisitEntity> visitsByVisitor1 = dbService.getVisitsByVisitorId(1);
        assertEquals(2, visitsByVisitor1.size());

        List<VisitEntity> visitsByVisitor2 = dbService.getVisitsByVisitorId(2);
        assertTrue(visitsByVisitor2.isEmpty());

        assertThrows(DataIntegrityViolationException.class, () ->
                jdbcTemplate.execute("INSERT INTO meet5_visit (user_id, profile_id) VALUES (2, 2)"));
    }

    @Test
    void isUserFraudulent() {
        dbService.saveUser(getTestUser(1));
        IntStream.range(2, 102)
                 .forEach(id -> {
                     dbService.saveUser(getTestUser(id));
                     dbService.saveVisit(VisitEntity.builder().visitor(1).profile(id).build());
                 });

        assertFalse(dbService.isUserFraudulent(1));

        dbService.saveUser(getTestUser(103));
        dbService.saveVisit(VisitEntity.builder().visitor(1).profile(103).build());

        assertTrue(dbService.isUserFraudulent(1));
    }

    @Test
    @Commit
    void getUserSequence() {
        // Some waste of sequence. Transaction rollback will not affect the sequence
        int id = dbService.getUserSequence();
        assertEquals(id + 1, dbService.getUserSequence());
    }

    @Test
    void saveUser() {
        dbService.saveUser(getTestUser(1));
        assertTrue(dbService.getUserById(1).isPresent());
    }

    @Test
    void saveVisit() {
        dbService.saveUser(getTestUser(1));
        dbService.saveVisit(VisitEntity.builder()
                                       .visitor(1)
                                       .profile(1)
                                       .build());
        assertEquals(1, dbService.getVisitsByVisitorId(1).get(0).getVisitor());
    }

}