package com.dgsw.meetlog.vote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    // 투표 제목
    @Column(nullable = false)
    private String title;

    // 상세 설명
    private String content;

    // 투표 마감 기한
    private LocalDate deadline;

    // 중복 투표 여부
    @Column(nullable = false)
    private Boolean isAllowingMultipleVotes;

    // 최대 중복 횟수
    private Integer maxNumberOfRepetitions;

    // 익명 투표 여부
    @Column(nullable = false)
    private Boolean isAnonymous;

    // 활성화 여부
    @Column(nullable = false)
    private Boolean isActive;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Vote create(String title,
                              String content,
                              LocalDate deadline,
                              Boolean isAllowingMultipleVotes,
                              Integer maxNumberOfRepetitions,
                              Boolean isAnonymous) {
        Vote vote = new Vote();
        vote.title = title;
        vote.content = content;
        vote.deadline = deadline;
        vote.isAllowingMultipleVotes = isAllowingMultipleVotes;
        vote.maxNumberOfRepetitions = maxNumberOfRepetitions;
        vote.isAnonymous = isAnonymous;
        vote.isActive = true;
        return vote;
    }

    public void update(String title,
                       String content,
                       LocalDate deadline,
                       Boolean isAllowingMultipleVotes,
                       Integer maxNumberOfRepetitions,
                       Boolean isAnonymous,
                       Boolean isActive) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.isAllowingMultipleVotes = isAllowingMultipleVotes;
        this.maxNumberOfRepetitions = maxNumberOfRepetitions;
        this.isAnonymous = isAnonymous;
        this.isActive = isActive;
    }
}
