package br.upe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) Long id;
    @Setter(AccessLevel.PROTECTED) boolean su;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String cpf;
}
