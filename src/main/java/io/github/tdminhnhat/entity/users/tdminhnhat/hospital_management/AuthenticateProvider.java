package io.github.tdminhnhat.entity.users.tdminhnhat.hospital_management;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
public class AuthenticateProvider {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "authen_name", length = 100, unique = true, nullable = false) @NonNull
    private String authenName;
    @Column(nullable = false)
    private boolean status;

    @PrePersist
    public void onPersist() {
        status = true;
    }
}
