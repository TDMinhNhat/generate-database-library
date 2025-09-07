package dev.tdminhnhat.entity.users.example.department;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
