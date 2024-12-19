package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@DiscriminatorValue("SystemAdmin")
public class SystemAdmin extends Userd {
    @OneToMany(
            targetEntity=br.upe.entities.Event.class,
            mappedBy="admin",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.EAGER
    )
    private @Setter(AccessLevel.PROTECTED) List<Event> events = new ArrayList<>();
}
