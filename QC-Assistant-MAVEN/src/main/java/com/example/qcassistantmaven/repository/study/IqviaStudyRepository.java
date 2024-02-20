package com.example.qcassistantmaven.repository.study;

import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IqviaStudyRepository extends JpaRepository<IqviaStudy, Long> {

    Optional<IqviaStudy> findFirstByName(String name);

    List<IqviaStudy> findAllByNameNot(String UNKNOWN);
}
