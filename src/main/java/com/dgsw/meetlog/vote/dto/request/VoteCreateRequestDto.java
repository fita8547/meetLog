package com.dgsw.meetlog.vote.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class VoteCreateRequestDto {
    private String title;
    private String content;
    private LocalDate deadline;
    private Boolean isAllowingMultipleVotes;
    private Integer maxNumberOfRepetitions;
    private Boolean isAnonymous;
    private List<ChoiceRequestDto> choices;
}
