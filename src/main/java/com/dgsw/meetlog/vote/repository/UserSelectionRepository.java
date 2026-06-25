package com.dgsw.meetlog.vote.repository;

import com.dgsw.meetlog.vote.entity.UserSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSelectionRepository extends JpaRepository<UserSelection, Integer> {
}
