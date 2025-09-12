package io.github.tdminhnhat.entity.users.tdminhnhat.pharmacy;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "manufacturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    private String address;

    private String contactNumber;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List<Drug> drugs;
}