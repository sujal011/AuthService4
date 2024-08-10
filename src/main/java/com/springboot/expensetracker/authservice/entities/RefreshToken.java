package com.springboot.expensetracker.authservice.entities;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RefreshToken {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private Instant expiryDate;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info", referencedColumnName = "user_id")
    private UserInfo userInfo;
}
