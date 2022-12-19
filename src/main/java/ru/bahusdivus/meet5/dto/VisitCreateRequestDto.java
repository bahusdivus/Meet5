package ru.bahusdivus.meet5.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class VisitCreateRequestDto {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer profileId;
}
