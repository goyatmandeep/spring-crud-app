package com.demo.javaApplication.Repository;

import com.demo.javaApplication.Entity.UserEntity;
import com.demo.javaApplication.SharedDTO.UserDTO;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserID(String userID);
    UserEntity findByEmailVerificationToken(String token);
}
