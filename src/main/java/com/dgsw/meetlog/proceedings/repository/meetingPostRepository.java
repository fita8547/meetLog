package com.dgsw.meetlog.proceedings.repository;

import com.dgsw.meetlog.proceedings.entity.meetingPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface meetingPostRepository extends JpaRepository<meetingPost, Integer> {
}