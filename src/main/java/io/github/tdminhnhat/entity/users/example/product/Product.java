package io.github.tdminhnhat.entity.users.example.product;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "products")
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String productName;
    @NonNull
    private String manufacturer;
    @NonNull
    private Double quantity;
    @NonNull
    private Double price;
    private String description;
    @ManyToOne @JoinColumn(name = "category_id")
    @NonNull
    private Category category;
}
