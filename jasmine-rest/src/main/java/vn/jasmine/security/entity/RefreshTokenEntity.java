package vn.jasmine.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshTokenEntity {

    @Id
    @UuidGenerator
    @Column(name = "refresh_token")
    private UUID refreshToken;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "username")
    private String username;

    @Column(nullable = false)
    private Instant expiryDate;

}
