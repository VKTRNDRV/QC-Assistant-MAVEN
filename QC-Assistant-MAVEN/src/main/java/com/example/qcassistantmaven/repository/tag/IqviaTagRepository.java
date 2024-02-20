package com.example.qcassistantmaven.repository.tag;

import com.example.qcassistantmaven.domain.entity.tag.IqviaTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IqviaTagRepository extends JpaRepository<IqviaTag, Long> {

}
