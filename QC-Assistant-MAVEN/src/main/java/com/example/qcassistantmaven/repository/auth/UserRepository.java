package com.example.qcassistantmaven.repository.auth;

import com.example.qcassistantmaven.domain.entity.auth.RoleEntity;
import com.example.qcassistantmaven.domain.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);

    List<UserEntity> findAllByRolesContaining(RoleEntity role);
}
