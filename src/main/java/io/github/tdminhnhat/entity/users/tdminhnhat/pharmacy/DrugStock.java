package io.github.tdminhnhat.entity.users.tdminhnhat.pharmacy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drug_stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Integer quantity;

    @NonNull
    private String location; // e.g., "Main Pharmacy", "Warehouse"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_stock_drug"))
    @NonNull
    private Drug drug;
}
