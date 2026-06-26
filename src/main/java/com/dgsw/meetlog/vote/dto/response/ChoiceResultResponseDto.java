package com.dgsw.meetlog.vote.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChoiceResultResponseDto {
    private Integer choiceId;
    private String content;
    private Integer count;
    private Double ratio;
}
