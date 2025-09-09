package io.github.tdminhnhat.entity.users.example.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String categoryName;
}
