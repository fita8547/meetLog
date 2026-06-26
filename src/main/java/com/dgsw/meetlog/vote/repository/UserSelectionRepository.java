package com.dgsw.meetlog.vote.repository;

import com.dgsw.meetlog.vote.entity.UserSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSelectionRepository extends JpaRepository<UserSelection, Integer> {
    boolean existsByVoteIdAndUserId(Integer voteId, Integer userId);
    Long countByVoteId(Integer voteId);
    Long countByChoiceId(Integer choiceId);
    Optional<UserSelection> findByVoteIdAndUserId(Integer voteId, Integer userId);
    void deleteAllByVoteId(Integer voteId);
}
