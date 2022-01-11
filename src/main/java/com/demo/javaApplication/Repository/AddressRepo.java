package com.demo.javaApplication.Repository;

import com.demo.javaApplication.Entity.AddressEntity;
import com.demo.javaApplication.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends CrudRepository<AddressEntity, Long> {
    AddressEntity findByAddressID(String addressID);
    List<AddressEntity> findAllByUserDetails(UserEntity userDetails);
}
