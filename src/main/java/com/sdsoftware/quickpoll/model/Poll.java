package com.sdsoftware.quickpoll.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false, unique = true)
    private String uniqueId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    private List<PollOption> options = new ArrayList<>();

    @PrePersist
    private void generateUniqueId() {
        if (uniqueId == null) {
            this.uniqueId = UUID.randomUUID().toString().substring(0, 8);
        }
    }
}
