package com.dgsw.meetlog.proceedings.controller;

import com.dgsw.meetlog.proceedings.dto.request.MeetingPostRequestDto;
import com.dgsw.meetlog.proceedings.dto.response.MeetingPostResponseDto;
import com.dgsw.meetlog.proceedings.service.MeetingPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meeting-posts")
@RequiredArgsConstructor
@Tag(name = "Meeting Post", description = "회의록 게시판 CRUD API")
public class MeetingPostController {

    private final MeetingPostService meetingPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회의록 생성", description = "회의록 게시글을 생성한다.")
    public MeetingPostResponseDto createMeetingPost(@RequestBody MeetingPostRequestDto request) {
        return meetingPostService.createMeetingPost(request);
    }

    @GetMapping
    @Operation(summary = "회의록 전체 조회", description = "회의록 게시글 목록을 조회한다.")
    public List<MeetingPostResponseDto> getMeetingPosts() {
        return meetingPostService.getMeetingPosts();
    }

    @GetMapping("/{meetingPostId}")
    @Operation(summary = "회의록 단건 조회", description = "회의록 게시글 하나를 조회한다.")
    public MeetingPostResponseDto getMeetingPost(@PathVariable Integer meetingPostId) {
        return meetingPostService.getMeetingPost(meetingPostId);
    }

    @PutMapping("/{meetingPostId}")
    @Operation(summary = "회의록 수정", description = "회의록 게시글을 수정한다.")
    public MeetingPostResponseDto updateMeetingPost(
            @PathVariable Integer meetingPostId,
            @RequestBody MeetingPostRequestDto request
    ) {
        return meetingPostService.updateMeetingPost(meetingPostId, request);
    }

    @DeleteMapping("/{meetingPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "회의록 삭제", description = "회의록 게시글을 삭제한다.")
    public void deleteMeetingPost(@PathVariable Integer meetingPostId) {
        meetingPostService.deleteMeetingPost(meetingPostId);
    }
}
