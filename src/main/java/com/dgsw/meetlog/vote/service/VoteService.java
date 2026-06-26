package com.dgsw.meetlog.vote.service;

import com.dgsw.meetlog.vote.dto.request.VoteCreateRequestDto;
import com.dgsw.meetlog.vote.dto.request.VoteSelectRequestDto;
import com.dgsw.meetlog.vote.dto.request.VoteUpdateRequestDto;
import com.dgsw.meetlog.vote.dto.response.ChoiceResponseDto;
import com.dgsw.meetlog.vote.dto.response.ChoiceResultResponseDto;
import com.dgsw.meetlog.vote.dto.response.VoteResponseDto;
import com.dgsw.meetlog.vote.dto.response.VoteResultResponseDto;
import com.dgsw.meetlog.vote.entity.Choice;
import com.dgsw.meetlog.vote.entity.UserSelection;
import com.dgsw.meetlog.vote.entity.Vote;
import com.dgsw.meetlog.vote.repository.ChoiceRepository;
import com.dgsw.meetlog.vote.repository.UserSelectionRepository;
import com.dgsw.meetlog.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final ChoiceRepository choiceRepository;
    private final UserSelectionRepository userSelectionRepository;

    @Transactional(readOnly = true)
    public VoteResponseDto getVote(Integer voteId) {
        Vote vote = findVote(voteId);
        List<Choice> choices = choiceRepository.findAllByVoteId(voteId);
        return toVoteResponse(vote, choices);
    }

    @Transactional(readOnly = true)
    public boolean hasVoted(Integer voteId, Integer userId) {
        return userSelectionRepository.existsByVoteIdAndUserId(voteId, userId);
    }

    @Transactional(readOnly = true)
    public VoteResultResponseDto getVoteResult(Integer voteId) {
        Integer totalVotes = userSelectionRepository.countByVoteId(voteId).intValue();
        List<Choice> choices = choiceRepository.findAllByVoteId(voteId);

        List<ChoiceResultResponseDto> results = choices.stream()
                .map(choice -> {
                    Integer count = userSelectionRepository.countByChoiceId(choice.getChoiceId()).intValue();
                    Double ratio = totalVotes == 0 ? 0.0 : (double) count / totalVotes * 100;
                    return ChoiceResultResponseDto.builder()
                            .choiceId(choice.getChoiceId())
                            .content(choice.getContent())
                            .count(count)
                            .ratio(ratio)
                            .build();
                })
                .toList();

        return VoteResultResponseDto.builder()
                .voteId(voteId)
                .totalVotes(totalVotes)
                .results(results)
                .build();
    }

    public VoteResponseDto createVote(VoteCreateRequestDto request) {
        Vote vote = Vote.create(
                request.getTitle(),
                request.getContent(),
                request.getDeadline(),
                Boolean.TRUE.equals(request.getIsAllowingMultipleVotes()),
                request.getMaxNumberOfRepetitions(),
                Boolean.TRUE.equals(request.getIsAnonymous())
        );
        Vote savedVote = voteRepository.save(vote);

        List<Choice> choices = request.getChoices().stream()
                .map(c -> Choice.create(savedVote.getVoteId(), c.getSortOrder(), c.getContent()))
                .map(choiceRepository::save)
                .toList();

        return toVoteResponse(savedVote, choices);
    }

    public void castVote(Integer voteId, VoteSelectRequestDto request) {
        Vote vote = findVote(voteId);
        if (!vote.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료된 투표입니다.");
        }
        if (request.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 id가 필요합니다.");
        }
        if (userSelectionRepository.existsByVoteIdAndUserId(voteId, request.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 투표하셨습니다.");
        }

        List<Integer> choiceIds = resolveChoiceIds(request);
        validateVotePolicy(vote, choiceIds);
        validateChoicesBelongToVote(voteId, choiceIds);

        choiceIds.stream()
                .map(choiceId -> UserSelection.create(request.getUserId(), voteId, choiceId))
                .forEach(userSelectionRepository::save);
    }

    public VoteResponseDto updateVote(Integer voteId, VoteUpdateRequestDto request) {
        Vote vote = findVote(voteId);
        vote.update(
                request.getTitle(),
                request.getContent(),
                request.getDeadline(),
                Boolean.TRUE.equals(request.getIsAllowingMultipleVotes()),
                request.getMaxNumberOfRepetitions(),
                Boolean.TRUE.equals(request.getIsAnonymous()),
                request.getIsActive()
        );

        if (request.getChoices() != null && !request.getChoices().isEmpty()) {
            choiceRepository.deleteAllByVoteId(voteId);
            List<Choice> newChoices = request.getChoices().stream()
                    .map(c -> Choice.create(voteId, c.getSortOrder(), c.getContent()))
                    .map(choiceRepository::save)
                    .toList();
            return toVoteResponse(vote, newChoices);
        }

        List<Choice> choices = choiceRepository.findAllByVoteId(voteId);
        return toVoteResponse(vote, choices);
    }

    public void closeVote(Integer voteId) {
        Vote vote = findVote(voteId);
        vote.update(
                vote.getTitle(),
                vote.getContent(),
                vote.getDeadline(),
                vote.getIsAllowingMultipleVotes(),
                vote.getMaxNumberOfRepetitions(),
                vote.getIsAnonymous(),
                false
        );
    }

    public void deleteVote(Integer voteId) {
        Vote vote = findVote(voteId);
        userSelectionRepository.deleteAllByVoteId(voteId);
        choiceRepository.deleteAllByVoteId(voteId);
        voteRepository.delete(vote);
    }

    private Vote findVote(Integer voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "투표를 찾을 수 없습니다."));
    }

    private List<Integer> resolveChoiceIds(VoteSelectRequestDto request) {
        Set<Integer> choiceIds = new LinkedHashSet<>();
        if (request.getChoiceIds() != null) {
            choiceIds.addAll(request.getChoiceIds());
        }
        if (request.getChoiceId() != null) {
            choiceIds.add(request.getChoiceId());
        }
        choiceIds.remove(null);

        if (choiceIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "선택지가 필요합니다.");
        }
        return choiceIds.stream().toList();
    }

    private void validateVotePolicy(Vote vote, List<Integer> choiceIds) {
        boolean allowsMultipleVotes = Boolean.TRUE.equals(vote.getIsAllowingMultipleVotes());
        if (!allowsMultipleVotes && choiceIds.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이 투표는 하나의 선택지만 고를 수 있습니다.");
        }

        Integer maxSelections = vote.getMaxNumberOfRepetitions();
        if (allowsMultipleVotes && maxSelections != null && choiceIds.size() > maxSelections) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "선택 가능한 개수를 초과했습니다.");
        }
    }

    private void validateChoicesBelongToVote(Integer voteId, List<Integer> choiceIds) {
        List<Integer> validChoiceIds = choiceRepository.findAllByVoteId(voteId).stream()
                .map(Choice::getChoiceId)
                .toList();
        if (!validChoiceIds.containsAll(choiceIds)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 투표에 없는 선택지가 포함되어 있습니다.");
        }
    }

    private VoteResponseDto toVoteResponse(Vote vote, List<Choice> choices) {
        return VoteResponseDto.builder()
                .voteId(vote.getVoteId())
                .title(vote.getTitle())
                .content(vote.getContent())
                .deadline(vote.getDeadline())
                .isAllowingMultipleVotes(vote.getIsAllowingMultipleVotes())
                .maxNumberOfRepetitions(vote.getMaxNumberOfRepetitions())
                .isAnonymous(vote.getIsAnonymous())
                .isActive(vote.getIsActive())
                .choices(choices.stream().map(this::toChoiceResponse).toList())
                .build();
    }

    private ChoiceResponseDto toChoiceResponse(Choice choice) {
        return ChoiceResponseDto.builder()
                .choiceId(choice.getChoiceId())
                .sortOrder(choice.getSortOrder())
                .content(choice.getContent())
                .build();
    }
}
