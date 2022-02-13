package com.demo.javaApplication.Repository;

import com.demo.javaApplication.Entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepo extends CrudRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findById(String id);
    PasswordResetTokenEntity findByPasswordResetToken(String passwordResetToken);
}
