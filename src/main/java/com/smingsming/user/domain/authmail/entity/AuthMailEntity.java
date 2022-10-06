package com.smingsming.user.domain.authmail.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "authmail")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthMailEntity {
    @Id
    private String id;
    private String email;
    private String authKey;

    @CreatedDate
    private LocalDateTime createDate;

    @Version
    private Integer version;

    @Builder
    public AuthMailEntity(String email, String authKey) {
        this.email = email;
        this.authKey = authKey;
    }
}
