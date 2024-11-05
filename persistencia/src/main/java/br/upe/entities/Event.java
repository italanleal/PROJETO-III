package br.upe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity @Getter @Setter @ToString
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Setter(AccessLevel.PROTECTED) Long id;

    private String title;
    private String description;
    private String director;
}
