package com.example.qcassistantmaven.repository.app;

import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedidataAppRepository extends JpaRepository<MedidataApp, Long> {

    Optional<MedidataApp> findFirstByName(String name);
}
