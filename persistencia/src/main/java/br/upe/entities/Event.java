package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @ToString
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
            orphanRemoval=true)
    private @Setter(AccessLevel.PROTECTED) List<Session> sessions = new ArrayList<>();
}
