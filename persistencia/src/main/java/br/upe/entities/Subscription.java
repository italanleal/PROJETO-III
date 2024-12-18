package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
public class Subscription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Setter(AccessLevel.PROTECTED) Long id;

    private LocalDate date;
    @ManyToOne(
            targetEntity=br.upe.entities.Session.class,
            cascade=CascadeType.PERSIST,
            fetch=FetchType.EAGER,
            optional=true
    )
    @JoinColumn(name="session_id", nullable=true, updatable=true)
    private Session session;
    @ManyToOne(
            targetEntity=br.upe.entities.SystemUser.class,
            cascade=CascadeType.PERSIST,
            fetch=FetchType.EAGER,
            optional=true
    )
    @JoinColumn(name="user_id", nullable=true, updatable=true)
    private SystemUser user;
}
