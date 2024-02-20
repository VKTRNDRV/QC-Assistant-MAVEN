package com.example.qcassistantmaven.repository.app;

import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IqviaAppRepository extends JpaRepository<IqviaApp, Long> {

    Optional<IqviaApp> findFirstByName(String name);
}
