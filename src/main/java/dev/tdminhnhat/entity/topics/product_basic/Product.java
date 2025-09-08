package dev.tdminhnhat.entity.topics.product_basic;

import dev.tdminhnhat.entity.AbstractEntityProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "products")
@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
public class Product extends AbstractEntityProperty {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name", length = 200, nullable = false) @NonNull
    private String productName;
    @Column(name = "description", length = 300)
    private String description;
    @Column(name = "price", nullable = false) @NonNull
    private Double price;
    @Column(name = "quantity", nullable = false) @NonNull
    private Double quantity;
    @ManyToOne @JoinColumn(name = "category_id", nullable = false) @NonNull
    private Category category;

    public Product(String createdBy, String updatedBy, @NonNull String productName, String description, @NonNull Double price, @NonNull Double quantity, @NonNull Category category) {
        super(createdBy, updatedBy);
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
}
