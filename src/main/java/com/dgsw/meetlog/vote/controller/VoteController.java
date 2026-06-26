package com.dgsw.meetlog.vote.controller;

import com.dgsw.meetlog.vote.dto.request.VoteCreateRequestDto;
import com.dgsw.meetlog.vote.dto.request.VoteSelectRequestDto;
import com.dgsw.meetlog.vote.dto.request.VoteUpdateRequestDto;
import com.dgsw.meetlog.vote.dto.response.VoteResponseDto;
import com.dgsw.meetlog.vote.dto.response.VoteResultResponseDto;
import com.dgsw.meetlog.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/get/{voteId}")
    public ResponseEntity<VoteResponseDto> getVote(@PathVariable Integer voteId) {
        return ResponseEntity.ok(voteService.getVote(voteId));
    }

    @GetMapping("/check/{voteId}/{userId}")
    public ResponseEntity<Boolean> hasVoted(
            @PathVariable Integer voteId,
            @PathVariable Integer userId
    ) {
        return ResponseEntity.ok(voteService.hasVoted(voteId, userId));
    }

    @GetMapping("/result/{voteId}")
    public ResponseEntity<VoteResultResponseDto> getVoteResult(@PathVariable Integer voteId) {
        return ResponseEntity.ok(voteService.getVoteResult(voteId));
    }

    @PostMapping("/create")
    public ResponseEntity<VoteResponseDto> createVote(@RequestBody VoteCreateRequestDto request) {
        return ResponseEntity.ok(voteService.createVote(request));
    }

    @PostMapping("/select/{voteId}")
    public ResponseEntity<Void> castVote(
            @PathVariable Integer voteId,
            @RequestBody VoteSelectRequestDto request
    ) {
        voteService.castVote(voteId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{voteId}")
    public ResponseEntity<VoteResponseDto> updateVote(
            @PathVariable Integer voteId,
            @RequestBody VoteUpdateRequestDto request
    ) {
        return ResponseEntity.ok(voteService.updateVote(voteId, request));
    }

    @PatchMapping("/close/{voteId}")
    public ResponseEntity<Void> closeVote(@PathVariable Integer voteId) {
        voteService.closeVote(voteId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{voteId}")
    public ResponseEntity<Void> deleteVote(@PathVariable Integer voteId) {
        voteService.deleteVote(voteId);
        return ResponseEntity.noContent().build();
    }
}
