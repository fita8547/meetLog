package com.dgsw.meetlog.vote.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class VoteResponseDto {
    private Integer voteId;
    private String title;
    private String content;
    private LocalDate deadline;
    private Boolean isAllowingMultipleVotes;
    private Integer maxNumberOfRepetitions;
    private Boolean isAnonymous;
    private Boolean isActive;
    private List<ChoiceResponseDto> choices;
}
