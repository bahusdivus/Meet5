package ru.bahusdivus.meet5.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.bahusdivus.meet5.Meet5ApplicationTests;
import ru.bahusdivus.meet5.dto.VisitCreateRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class VisitServiceTest extends Meet5ApplicationTests {
    @MockBean
    DbService dbService;
    @Autowired
    VisitService visitService;

    @Test
    void createVisit() {
        visitService.createVisit(VisitCreateRequestDto.builder()
                                                      .userId(1)
                                                      .profileId(1)
                                                      .build());

        verify(dbService, times(1)).saveVisit(any());
    }
}