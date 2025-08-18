package org.yunusgedik.user.Model.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yunusgedik.user.Model.UserStats.UserStats;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "appUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserStats stats;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "userRoles", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "role")
    private Set<String> roles;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;

        if (stats == null) {
            stats = new UserStats();
            stats.setUser(this);
        }

        if (roles == null || roles.isEmpty()) {
            roles = Set.of("ROLE_USER"); // default role
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}