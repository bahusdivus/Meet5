package ru.bahusdivus.meet5.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class UserEntity {
    private int id;
    private String name;
    private LocalDate birthdate;
    private LocalDateTime regDate;
    private String note;

    public static Optional<UserEntity> of(ResultSet rs) throws SQLException {
        if (rs.next()) {
            UserEntity user = UserEntity.builder()
                                        .id(rs.getInt(1))
                                        .name(rs.getString(2))
                                        .birthdate(rs.getTimestamp(3)
                                                     .toLocalDateTime().toLocalDate())
                                        .regDate(rs.getTimestamp(4).toLocalDateTime())
                                        .note(rs.getString(5))
                                        .build();
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
