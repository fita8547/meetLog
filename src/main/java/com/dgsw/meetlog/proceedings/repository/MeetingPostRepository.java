package com.dgsw.meetlog.proceedings.repository;

import com.dgsw.meetlog.proceedings.entity.MeetingPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingPostRepository extends JpaRepository<MeetingPost, Integer> {
}
