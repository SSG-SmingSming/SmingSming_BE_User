package com.smingsming.user.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;


@Data
@Entity
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauth;

    @NotNull
    private String userEmail;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    private String userThumbnail;
    private String userBackGround;


    @CreationTimestamp
    private Timestamp createTime;

    @NotNull
    @Column(name = "is_leave")
    private boolean leave;

    private Timestamp leaveDate;

    @NotNull
    @Column(name = "is_membership")
    private boolean membership;

    private String membershipGrade;
    private Date expiredDate;

}
