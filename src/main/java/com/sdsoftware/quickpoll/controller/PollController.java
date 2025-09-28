package com.sdsoftware.quickpoll.controller;

import com.sdsoftware.quickpoll.dto.PollRequest;
import com.sdsoftware.quickpoll.dto.PollResponse;
import com.sdsoftware.quickpoll.service.PollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/polls")
@CrossOrigin(origins = {"http://localhost:3000", "https://samujjaldas97.github.io"})
public class PollController {

    @Autowired
    private PollService pollService;

    @PostMapping
    public ResponseEntity<PollResponse> createPoll(@Valid @RequestBody PollRequest pollRequest) {
        PollResponse pollResponse = pollService.createPoll(pollRequest);

        // Create a URI for the newly created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{uniqueId}")
                .buildAndExpand(pollResponse.getUniqueId()).toUri();

        // Return a 201 Created status with the location and the response body
        return ResponseEntity.created(location).body(pollResponse);
    }

    @GetMapping("/{uniqueId}")
    public ResponseEntity<PollResponse> getPoll(@PathVariable String uniqueId) {
        PollResponse pollResponse = pollService.getPollByUniqueId(uniqueId);
        return ResponseEntity.ok(pollResponse);
    }

    @PostMapping("/vote/{optionId}")
    public ResponseEntity<Void> castVote(@PathVariable Long optionId) {
        pollService.castVote(optionId);
        return ResponseEntity.ok().build();
    }

}
