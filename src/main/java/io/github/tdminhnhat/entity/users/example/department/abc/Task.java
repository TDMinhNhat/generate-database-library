package io.github.tdminhnhat.entity.users.example.department.abc;

import io.github.tdminhnhat.entity.AbstractEntityProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Task extends AbstractEntityProperty {
    @Id
    private Long id;
}
