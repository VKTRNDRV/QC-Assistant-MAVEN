package com.example.qcassistantmaven.repository.tag;

import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidataTagRepository extends JpaRepository<MedidataTag, Long> {

}
