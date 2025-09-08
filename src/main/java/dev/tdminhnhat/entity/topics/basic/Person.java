package dev.tdminhnhat.entity.topics.basic;

import dev.tdminhnhat.entity.AbstractEntityProperty;
import dev.tdminhnhat.entity.topics.basic.enums.PersonStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Table(name = "students")
@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
public class Person extends AbstractEntityProperty {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    @NonNull
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    @NonNull
    private String lastName;

    @Column(name = "sex", nullable = false)
    @NonNull
    private Boolean sex;

    @Column(name = "birth_date", nullable = false)
    @NonNull
    private LocalDate birthDate;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "user_name", length = 50, nullable = false, unique = true)
    @NonNull
    private String username;

    @Column(name = "email", length = 200, nullable = false, unique = true)
    @NonNull
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    @NonNull
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private PersonStatus status;

    public Person(String createdBy, String updatedBy, @NonNull String firstName, @NonNull String lastName, @NonNull Boolean sex, @NonNull LocalDate birthDate, String address, @NonNull String username, @NonNull String email, @NonNull String password) {
        super(createdBy, updatedBy);
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.address = address;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    public void onPrePersist() {
        super.onPrePersist();
        this.status = PersonStatus.ACTIVE;
    }

    @PreUpdate
    public void onPreUpdate() {
        super.onPreUpdate();
    }
}
