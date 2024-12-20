package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class BaseEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) Long id;

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
            fetch=FetchType.EAGER
    )
    private @Setter(AccessLevel.PROTECTED) List<Session> sessions = new ArrayList<>();
}
