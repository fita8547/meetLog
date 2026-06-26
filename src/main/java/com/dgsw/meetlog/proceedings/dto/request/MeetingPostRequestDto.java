package com.dgsw.meetlog.proceedings.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MeetingPostRequestDto {

    private Integer userId;
    private String title;
    private String content;
}
