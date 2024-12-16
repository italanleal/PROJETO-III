package br.upe.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
public class SystemAdmin extends User {
    @Setter(AccessLevel.PROTECTED) boolean su = true;

    @OneToMany(
            targetEntity=br.upe.entities.Event.class,
            mappedBy="admin",
            cascade= CascadeType.ALL,
            orphanRemoval=true,
            fetch= FetchType.LAZY
    )
    private @Setter(AccessLevel.PROTECTED) List<Event> events = new ArrayList<>();

}
