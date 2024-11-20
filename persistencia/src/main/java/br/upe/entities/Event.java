package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Setter(AccessLevel.PROTECTED) Long id;

    private String title;
    private String description;
    private String director;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(
            targetEntity=br.upe.entities.Session.class,
            mappedBy="event",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.LAZY
    )
    private @Setter(AccessLevel.PROTECTED) List<Session> sessions = new ArrayList<>();

    @OneToMany(
            targetEntity=br.upe.entities.Submission.class,
            mappedBy="event",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.LAZY
    )
    private @Setter(AccessLevel.PROTECTED) List<Submission> submissions = new ArrayList<>();

    @ManyToOne(
            targetEntity=br.upe.entities.Event.class,
            cascade=CascadeType.PERSIST,
            fetch=FetchType.LAZY,
            optional=true
    )
    @JoinColumn(name="admin_id", nullable=true, updatable=true)
    private SystemAdmin admin;
}
