package io.github.tdminhnhat.entity.users.example.department;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "departments")
@Data
@RequiredArgsConstructor
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shortName;
    private String fullName;
    private Boolean status;
}
