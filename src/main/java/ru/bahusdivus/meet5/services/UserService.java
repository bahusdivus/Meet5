package ru.bahusdivus.meet5.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bahusdivus.meet5.dto.UserCreateRequestDto;
import ru.bahusdivus.meet5.entities.UserEntity;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final DbService dbService;

    public UserService(DbService dbService) {
        this.dbService = dbService;
    }

    public boolean isUserFraudulent(int id) {
        return dbService.isUserFraudulent(id);
    }

    @Transactional
    public int createUserGetId(UserCreateRequestDto user) {
        int id = dbService.getUserSequence();
        UserEntity userEntity = UserEntity.builder()
                                          .id(id)
                                          .name(user.getName())
                                          .birthdate(user.getBirthdate())
                                          .regDate(LocalDateTime.now())
                                          .build();
        dbService.saveUser(userEntity);
        return id;
    }
}
