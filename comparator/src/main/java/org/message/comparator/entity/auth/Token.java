package org.message.comparator.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import org.message.comparator.util.TokenType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "token")
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String jwtToken;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public DashboardUser dashboardUser;
}
