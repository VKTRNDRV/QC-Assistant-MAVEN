package com.example.qcassistantmaven.repository.study;

import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedableStudyRepository extends JpaRepository<MedableStudy, Long> {

    Optional<MedableStudy> findFirstByName(String name);

    List<MedableStudy> findAllByNameNot(String UNKNOWN);
}
