package com.dgsw.meetlog.proceedings.dto.response;

import com.dgsw.meetlog.proceedings.entity.MeetingPost;
import lombok.Getter;

@Getter
public class MeetingPostResponseDto {

    private Integer meetingPostId;
    private Integer userId;
    private String title;
    private String content;

    public MeetingPostResponseDto(MeetingPost post) {
        this.meetingPostId = post.getMeetingPostId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public static MeetingPostResponseDto from(MeetingPost post) {
        return new MeetingPostResponseDto(post);
    }
}
