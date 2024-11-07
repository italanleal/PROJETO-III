package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
public class Session {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Setter(AccessLevel.PROTECTED) Long id;

    private String title;
    private String description;
    private String guest;
    private String local;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(
            targetEntity=br.upe.entities.Event.class,
            cascade=CascadeType.PERSIST,
            fetch=FetchType.LAZY,
            optional=true
    )
    @JoinColumn(name="event_id", nullable=true, updatable=true)
    private Event event;

    @OneToOne(
            targetEntity=br.upe.entities.SubEvent.class,
            mappedBy="session",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.LAZY
    )
    private SubEvent subEvent;

}
