package com.example.qcassistantmaven.repository.sponsor;

import com.example.qcassistantmaven.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedableSponsorRepository extends JpaRepository<MedableSponsor, Long> {

    Optional<MedableSponsor> findFirstByName(String name);

    List<MedableSponsor> findAllByNameNot(String UNKNOWN);
}
