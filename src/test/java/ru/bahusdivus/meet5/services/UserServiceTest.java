package ru.bahusdivus.meet5.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.bahusdivus.meet5.Meet5ApplicationTests;
import ru.bahusdivus.meet5.dto.UserCreateRequestDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest extends Meet5ApplicationTests {
    @MockBean
    DbService dbService;
    @Autowired
    UserService userService;

    @Test
    void isUserFraudulent() {
        when(dbService.isUserFraudulent(1)).thenReturn(true);
        when(dbService.isUserFraudulent(2)).thenReturn(false);

        assertTrue(userService.isUserFraudulent(1));
        assertFalse(userService.isUserFraudulent(2));
    }

    @Test
    void createUserGetId() {
        when(dbService.getUserSequence()).thenReturn(1);
        UserCreateRequestDto dto = UserCreateRequestDto.builder()
                                                       .name("test")
                                                       .birthdate(LocalDate.now())
                                                       .build();

        assertEquals(1, userService.createUserGetId(dto));
        verify(dbService, times(1)).saveUser(any());
    }
}