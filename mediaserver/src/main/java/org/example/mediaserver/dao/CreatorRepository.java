package org.example.mediaserver.dao;

import org.example.mediaserver.model.CreatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<CreatorEntity, String> {

}