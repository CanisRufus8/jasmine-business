package vn.jasmine.security.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLE")
public class RoleEntity {
    @Id
    @UuidGenerator
    @Column(name="id")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name; // eg: ADMIN, USER, MODERATOR

    @Column(length = 500)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

}
