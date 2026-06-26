package com.dgsw.meetlog.vote.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VoteSelectRequestDto {
    private Integer userId;
    private Integer choiceId;
    private List<Integer> choiceIds;
}
