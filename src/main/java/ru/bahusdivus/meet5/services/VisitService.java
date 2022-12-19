package ru.bahusdivus.meet5.services;

import org.springframework.stereotype.Service;
import ru.bahusdivus.meet5.dto.VisitCreateRequestDto;
import ru.bahusdivus.meet5.entities.VisitEntity;

@Service
public class VisitService {
    private final DbService dbService;

    public VisitService(DbService dbService) {
        this.dbService = dbService;
    }

    public void createVisit(VisitCreateRequestDto visit) {
        dbService.saveVisit(VisitEntity.builder()
                                       .visitor(visit.getUserId())
                                       .profile(visit.getProfileId())
                                       .build());
    }
}
