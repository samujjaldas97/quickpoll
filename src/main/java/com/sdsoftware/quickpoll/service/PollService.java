package com.sdsoftware.quickpoll.service;

import com.sdsoftware.quickpoll.dto.PollRequest;
import com.sdsoftware.quickpoll.dto.PollResponse;
import com.sdsoftware.quickpoll.exception.ResourceNotFoundException;
import com.sdsoftware.quickpoll.model.Poll;
import com.sdsoftware.quickpoll.model.PollOption;
import com.sdsoftware.quickpoll.repository.PollOptionRepository;
import com.sdsoftware.quickpoll.repository.PollRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PollOptionRepository pollOptionRepository;

    @Transactional
    public PollResponse createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();
        poll.setQuestion(pollRequest.getQuestion());

        List<PollOption> options = pollRequest.getOptions().stream().map(optionText -> {
            PollOption option = new PollOption();
            option.setOptionText(optionText);
            return option;
        }).collect(Collectors.toList());

        poll.setOptions(options);
        Poll savedPoll = pollRepository.save(poll);
        return new PollResponse(savedPoll);
    }

    public PollResponse getPollByUniqueId(String uniqueId) {
        Poll poll = pollRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll not found with id: " + uniqueId));
        return new PollResponse(poll);
    }

    @Transactional
    public void castVote(Long optionId) {
        PollOption option = pollOptionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: " + optionId));
        option.setVoteCount(option.getVoteCount() + 1);
        pollOptionRepository.save(option);
    }
}
