package com.demo.javaApplication.Entity;

import com.demo.javaApplication.Shared.UserConstants;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "password_reset_token")
public class PasswordResetTokenEntity implements Serializable {

    private static final long serialVersionUID = 42331L;

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name="users_id")
    private UserEntity userDetails;

    @Column(nullable = false)
    private String passwordResetToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userDetails;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userDetails = userEntity;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
}
