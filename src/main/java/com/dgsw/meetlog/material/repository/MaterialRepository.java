package com.dgsw.meetlog.material.repository;

import com.dgsw.meetlog.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
