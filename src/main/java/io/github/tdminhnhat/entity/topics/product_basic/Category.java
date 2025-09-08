package io.github.tdminhnhat.entity.topics.product_basic;

import io.github.tdminhnhat.entity.AbstractEntityProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "categories")
@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
public class Category extends AbstractEntityProperty {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name", nullable = false) @NonNull
    private String categoryName;

    public Category(String createdBy, String updatedBy, @NonNull String categoryName) {
        super(createdBy, updatedBy);
        this.categoryName = categoryName;
    }
}