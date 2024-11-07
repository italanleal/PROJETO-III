package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity @Getter @Setter @ToString
public class Session {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) Long id;

    private String title;
    private String description;
    private String guest;
    private String venue;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(targetEntity=br.upe.entities.Event.class)
    @JoinColumn(name="event_id", nullable=false, updatable=false)
    private Event event;
}
