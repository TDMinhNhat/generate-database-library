package io.github.tdminhnhat.entity.users.tdminhnhat.pharmacy;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table(name = "drugs")
@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private String genericName;

    @Column(nullable = false)
    private String dosageForm; // e.g., Tablet, Syrup, Injection

    @Column(nullable = false)
    private String strength; // e.g., "500mg", "10ml"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_drug_manufacturer"))
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_drug_category"))
    private DrugCategory category;

    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrugStock> stocks;
}
