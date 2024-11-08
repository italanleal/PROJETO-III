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
public class SystemUser extends User{
    @Setter(AccessLevel.PROTECTED) boolean su = false;

    @OneToMany(
            targetEntity=br.upe.entities.Subscription.class,
            mappedBy="user",
            cascade= CascadeType.ALL,
            orphanRemoval=true,
            fetch= FetchType.LAZY
    )
    private @Setter(AccessLevel.PROTECTED) List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(
            targetEntity=br.upe.entities.Submission.class,
            mappedBy="user",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.LAZY
    )
    private @Setter(AccessLevel.PROTECTED) List<Submission> submissions = new ArrayList<>();
}
