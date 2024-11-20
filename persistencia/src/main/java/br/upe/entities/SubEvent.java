package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
public class SubEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) Long id;

    private String title;
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;
    @OneToOne(
            targetEntity=br.upe.entities.Session.class,
            optional=true,
            fetch=FetchType.LAZY,
            cascade=CascadeType.PERSIST
    )
    @JoinColumn(name="session_id", unique=true, nullable=true, updatable=true)
    private Session session;
}
