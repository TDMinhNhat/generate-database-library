package io.github.tdminhnhat.entity.users.tdminhnhat.pharmacy;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "drug_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Drug> drugs;
}