package ru.bahusdivus.meet5.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bahusdivus.meet5.dto.VisitCreateRequestDto;
import ru.bahusdivus.meet5.services.VisitService;

import javax.validation.Valid;

@RestController
@RequestMapping("/visit")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("/")
    public void createVisit(@Valid @RequestBody VisitCreateRequestDto visit) {
        visitService.createVisit(visit);
    }
}
