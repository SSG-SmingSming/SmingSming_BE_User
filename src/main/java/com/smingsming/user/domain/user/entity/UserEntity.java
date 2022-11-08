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

    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
    private String providerId;  // oauth2를 이용할 경우 아이디값


    @NotNull
    private int userType;

    @NotNull
    private String userEmail;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    private String uuid;

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

    public void updateThumbnail(String userThumbnail) {
        this.userThumbnail = userThumbnail;
    }

//    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
//    public UserEntity(String role, int userType, String userEmail, String password, String nickname, String userThumbnail, boolean leave, boolean membership) {
//        this.role = role;
//        this.userType = userType;
//        this.userEmail = userEmail;
//        this.password = password;
//        this.nickname = nickname;
//        this.userThumbnail = userThumbnail;
//        this.leave = leave;
//        this.membership = membership;
//    }

//    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
//    public UserEntity(String role, int userType, String userEmail, String password, String nickname, String userThumbnail, boolean leave, boolean membership, String provider, String providerId, String uuid) {
//        this.role = role;
//        this.userType = userType;
//        this.userEmail = userEmail;
//        this.password = password;
//        this.nickname = nickname;
//        this.userThumbnail = userThumbnail;
//        this.leave = leave;
//        this.membership = membership;
//        this.provider = provider;
//        this.providerId = providerId;
//        this.uuid = uuid;
//    }


}