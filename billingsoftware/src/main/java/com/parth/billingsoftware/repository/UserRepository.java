package com.parth.billingsoftware.repository;

import com.parth.billingsoftware.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserByUserId(String userId);

    // It's good practice to have queries that respect the soft-delete flag
    List<UserEntity> findAllByIsDeletedFalse();


}
