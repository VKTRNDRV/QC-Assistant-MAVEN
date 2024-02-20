package com.example.qcassistantmaven.repository.study.environment;

import com.example.qcassistantmaven.domain.entity.study.environment.IqviaEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IqviaEnvironmentRepository extends JpaRepository<IqviaEnvironment, Long> {

}
