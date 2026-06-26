package com.dgsw.meetlog.vote.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChoiceRequestDto {
    private Integer sortOrder;
    private String content;
}
