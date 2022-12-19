package ru.bahusdivus.meet5;

import org.springframework.boot.test.context.SpringBootTest;
import ru.bahusdivus.meet5.entities.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class Meet5ApplicationTests {
    protected static UserEntity getTestUser(int id) {
        return UserEntity.builder()
                         .id(id)
                         .name("test")
                         .birthdate(LocalDate.now())
                         .regDate(LocalDateTime.now())
                         .build();
    }
}
