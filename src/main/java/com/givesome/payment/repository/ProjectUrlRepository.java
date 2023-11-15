package com.givesome.payment.repository;

import com.givesome.payment.entity.ProjectUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectUrlRepository extends JpaRepository<ProjectUrlEntity,Integer> {

    Optional<ProjectUrlEntity> findByProjectId(Integer projectId);
}
