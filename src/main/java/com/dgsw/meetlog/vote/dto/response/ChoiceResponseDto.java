package com.dgsw.meetlog.vote.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChoiceResponseDto {
    private Integer choiceId;
    private Integer sortOrder;
    private String content;
}
