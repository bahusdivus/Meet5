package ru.bahusdivus.meet5.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.bahusdivus.meet5.constants.FraudulentStatus;
import ru.bahusdivus.meet5.dto.UserCreateRequestDto;
import ru.bahusdivus.meet5.services.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    UserService userService;

    @Test
    void isFraudulent() {
        when(userService.isUserFraudulent(1)).thenReturn(true);
        when(userService.isUserFraudulent(2)).thenReturn(false);
        String url = "http://localhost:%s/user/visit/%s";

        String user1 = restTemplate.getForObject(String.format(url, port, 1), String.class);
        assertEquals(FraudulentStatus.FRAUDULENT.toString(), user1);

        String user2 = restTemplate.getForObject(String.format(url, port, 2), String.class);
        assertEquals(FraudulentStatus.NOT_FRAUDULENT.toString(), user2);
    }

    @Test
    void saveUserGetId() {
        when(userService.createUserGetId(any())).thenReturn(1)
                                                .thenThrow(IllegalArgumentException.class);
        String url = "http://localhost:" + port + "/user/";

        UserCreateRequestDto dto = UserCreateRequestDto.builder().name("test").birthdate(LocalDate.now()).build();
        assertEquals("1", restTemplate.postForObject(url, dto, String.class));

        assertEquals(INTERNAL_SERVER_ERROR, restTemplate.postForEntity(url, dto, String.class).getStatusCode());

        dto = UserCreateRequestDto.builder().birthdate(LocalDate.now()).build();
        assertEquals(BAD_REQUEST, restTemplate.postForEntity(url, dto, String.class).getStatusCode());
    }
}