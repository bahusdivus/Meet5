package ru.bahusdivus.meet5.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class VisitEntity {
    private LocalDateTime visitTime;
    private int visitor;
    private int profile;
    private String metadata;

    public static List<VisitEntity> listFrom(ResultSet rs) throws SQLException {
        List<VisitEntity> visits = new ArrayList<>();
        while (rs.next()) {
            VisitEntity visit = VisitEntity.builder()
                                        .visitTime(rs.getTimestamp(1).toLocalDateTime())
                                        .visitor(rs.getInt(2))
                                        .profile(rs.getInt(3))
                                        .metadata(rs.getString(4))
                                        .build();
            visits.add(visit);
        }
        return visits;
    }
}
