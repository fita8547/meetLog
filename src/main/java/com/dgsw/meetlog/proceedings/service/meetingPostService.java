package com.dgsw.meetlog.proceedings.service;

import com.dgsw.meetlog.proceedings.dto.request.MeetingPostRequestDto;
import com.dgsw.meetlog.proceedings.dto.response.MeetingPostResponseDto;
import com.dgsw.meetlog.proceedings.entity.MeetingPost;
import com.dgsw.meetlog.proceedings.repository.MeetingPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingPostService {

    private final MeetingPostRepository meetingPostRepository;

    @Transactional
    public MeetingPostResponseDto createMeetingPost(MeetingPostRequestDto request) {
        MeetingPost post = MeetingPost.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return MeetingPostResponseDto.from(meetingPostRepository.save(post));
    }

    public List<MeetingPostResponseDto> getMeetingPosts() {
        return meetingPostRepository.findAll()
                .stream()
                .map(MeetingPostResponseDto::from)
                .toList();
    }

    public MeetingPostResponseDto getMeetingPost(Integer meetingPostId) {
        return MeetingPostResponseDto.from(findMeetingPost(meetingPostId));
    }

    @Transactional
    public MeetingPostResponseDto updateMeetingPost(Integer meetingPostId, MeetingPostRequestDto request) {
        MeetingPost post = findMeetingPost(meetingPostId);
        post.update(request.getTitle(), request.getContent());
        return MeetingPostResponseDto.from(post);
    }

    @Transactional
    public void deleteMeetingPost(Integer meetingPostId) {
        meetingPostRepository.delete(findMeetingPost(meetingPostId));
    }

    private MeetingPost findMeetingPost(Integer meetingPostId) {
        return meetingPostRepository.findById(meetingPostId)
                .orElseThrow(() -> new IllegalArgumentException("meeting post not found: " + meetingPostId));
    }
}
