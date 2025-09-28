package com.sdsoftware.quickpoll.dto;

import com.sdsoftware.quickpoll.model.Poll;
import com.sdsoftware.quickpoll.model.PollOption;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PollResponse {

    private String uniqueId;
    private String question;
    private List<PollOptionResponse> options;

    public PollResponse(Poll poll) {
        this.uniqueId = poll.getUniqueId();
        this.question = poll.getQuestion();
        this.options = poll.getOptions().stream()
                .map(PollOptionResponse::new)
                .collect(Collectors.toList());
    }

    // Inner class for option responses
    @Data
    public static class PollOptionResponse {
        private Long id;
        private String optionText;
        private long voteCount;

        public PollOptionResponse(PollOption option) {
            this.id = option.getId();
            this.optionText = option.getOptionText();
            this.voteCount = option.getVoteCount();
        }
    }
}
