package dev.tdminhnhat.entity.topics.basic;

import dev.tdminhnhat.entity.AbstractEntityProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "departments")
@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
public class Department extends AbstractEntityProperty {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_name", length = 30, nullable = false, unique = true)
    @NonNull
    private String shortName;

    @Column(name = "full_name", length = 100, nullable = false, unique = true)
    @NonNull
    private String fullName;

    @Column(name = "description", length = 300)
    private String description;

    public Department(@NonNull String shortName, @NonNull String fullName, String description) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.description = description;
    }

    @PrePersist
    public void onPrePersist() {
        super.onPrePersist();
    }

    @PreUpdate
    public void onPreUpdate() {
        super.onPreUpdate();
    }
}
