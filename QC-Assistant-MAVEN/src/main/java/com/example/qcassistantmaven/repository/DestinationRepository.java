package com.example.qcassistantmaven.repository;

import com.example.qcassistantmaven.domain.entity.destination.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<Destination> findFirstByName(String name);

    List<Destination> findAllByNameNot(String UNKNOWN);
}
