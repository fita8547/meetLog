package com.dgsw.meetlog.vote.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VoteResultResponseDto {
    private Integer voteId;
    private Integer totalVotes;
    private List<ChoiceResultResponseDto> results;
}
