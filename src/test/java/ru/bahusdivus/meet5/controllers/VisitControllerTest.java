package ru.bahusdivus.meet5.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.bahusdivus.meet5.dto.VisitCreateRequestDto;
import ru.bahusdivus.meet5.services.VisitService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VisitControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    VisitService visitService;

    @Test
    void createVisit() {
        String url = "http://localhost:" + port + "/visit/";

        VisitCreateRequestDto dto = VisitCreateRequestDto.builder().userId(1).profileId(1).build();
        assertEquals(OK, restTemplate.postForEntity(url, dto, String.class).getStatusCode());

        doThrow(IllegalArgumentException.class).when(visitService).createVisit(any());
        assertEquals(INTERNAL_SERVER_ERROR, restTemplate.postForEntity(url, dto, String.class).getStatusCode());

        dto = VisitCreateRequestDto.builder().userId(1).build();
        assertEquals(BAD_REQUEST, restTemplate.postForEntity(url, dto, String.class).getStatusCode());

    }
}