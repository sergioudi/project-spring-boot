package com.github.sergioudi.infrastructure.video.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoJpaEntity, String> {

    Page<VideoJpaEntity> findAll(Specification<VideoJpaEntity> whereClause, Pageable page);

    @Query(value = "select c.id from Video c where c.id in :ids")
    List<String> existsByIds(@Param("ids") List<String> ids);
}
