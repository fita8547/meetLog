package com.dgsw.meetlog.vote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer choiceId;

    // 연관 투표 id
    @Column(nullable = false)
    private Integer voteId;

    // 투표 내부 정렬 순서
    @Column(nullable = false)
    private Integer sortOrder;

    // 선택지 내용
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static Choice create(Integer voteId, Integer sortOrder, String content) {
        Choice choice = new Choice();
        choice.voteId = voteId;
        choice.sortOrder = sortOrder;
        choice.content = content;
        return choice;
    }

    public void update(Integer sortOrder, String content) {
        this.sortOrder = sortOrder;
        this.content = content;
    }
}
