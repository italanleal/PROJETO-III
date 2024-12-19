package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@DiscriminatorValue("SystemUser")
public class SystemUser extends Userd {
    @OneToMany(
            targetEntity=br.upe.entities.Subscription.class,
            mappedBy="user",
            cascade= CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.EAGER
    )
    private @Setter(AccessLevel.PROTECTED) List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(
            targetEntity=br.upe.entities.Submission.class,
            mappedBy="user",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.EAGER
    )
    private @Setter(AccessLevel.PROTECTED) List<Submission> submissions = new ArrayList<>();

    @OneToMany(
            targetEntity=br.upe.entities.Certification.class,
            mappedBy="user",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.EAGER
    )
    private @Setter(AccessLevel.PROTECTED) List<Certification> certifications = new ArrayList<>();
}
