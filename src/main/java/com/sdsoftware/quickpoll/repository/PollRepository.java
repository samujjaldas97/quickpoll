package com.sdsoftware.quickpoll.repository;

import com.sdsoftware.quickpoll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findByUniqueId(String uniqueId);
}
