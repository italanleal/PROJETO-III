package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@DiscriminatorValue("Event")
public class Event extends BaseEvent {
    @OneToMany(
            targetEntity=br.upe.entities.SubEvent.class,
            mappedBy="event",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.EAGER
    )
    private @Setter(AccessLevel.PROTECTED) List<SubEvent> subEvents = new ArrayList<>();

    @OneToMany(
            targetEntity=br.upe.entities.Submission.class,
            mappedBy="event",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.EAGER
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
