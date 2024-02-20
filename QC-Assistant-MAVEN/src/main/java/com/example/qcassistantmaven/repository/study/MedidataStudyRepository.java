package com.example.qcassistantmaven.repository.study;

import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedidataStudyRepository extends JpaRepository<MedidataStudy, Long> {

    Optional<MedidataStudy> findFirstByName(String name);

    List<MedidataStudy> findAllByNameNot(String UNKNOWN);
}
