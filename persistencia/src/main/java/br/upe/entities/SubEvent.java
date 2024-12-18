package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
@DiscriminatorValue("SubEvent")
public class SubEvent extends BaseEvent {
    @ManyToOne(
            targetEntity=br.upe.entities.Event.class,
            cascade=CascadeType.PERSIST,
            fetch=FetchType.LAZY,
            optional=true
    )
    @JoinColumn(name="event_id", nullable=true, updatable=true)
    private Event event;
}
