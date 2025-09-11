package io.github.tdminhnhat.entity.users.example.department;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
public class Employee {
    @Id
    private Long id;
    private String employeeName;
    private Boolean sex;
    private String address;
    private LocalDate birthDate;
}
