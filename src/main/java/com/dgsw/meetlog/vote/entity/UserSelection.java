package com.dgsw.meetlog.vote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.h2.engine.User;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "userSelection")
public class UserSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userSelectionId;

    // 투표한 사용자 id
    @Column(nullable = false)
    private Integer userId;

    // 투표 id
    @Column(nullable = false)
    private Integer voteId;

    // 선택한 선택지 id
    @Column(nullable = false)
    private Integer choiceId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public static UserSelection create(Integer userId, Integer voteId, Integer choiceId) {
        UserSelection selection = new UserSelection();
        selection.userId = userId;
        selection.voteId = voteId;
        selection.choiceId = choiceId;
        return selection;
    }
}
