package com.smingsming.user.domain.user.entity;

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
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;    // ROLE_USER

    @NotNull
    private int userType;

    @NotNull
    private String userEmail;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    private String userThumbnail;

    @CreationTimestamp
    private Timestamp createTime;

    @NotNull
    @Column(name = "is_leave")
    private boolean leave;

    private Timestamp leaveDate;

    @NotNull
    @Column(name = "is_membership")
    private boolean membership;

    private Date expiredDate;


    public void updatePwd(String newPwd) {
        this.password = newPwd;
    }

    public UserEntity(String role, int userType, String userEmail, String password, String nickname, String userThumbnail, boolean leave, boolean membership) {
        this.role = role;
        this.userType = userType;
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
        this.userThumbnail = userThumbnail;
        this.leave = leave;
        this.membership = membership;
    }
}