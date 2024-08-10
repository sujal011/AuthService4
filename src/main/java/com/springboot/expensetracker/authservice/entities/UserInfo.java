package com.springboot.expensetracker.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInfo {

    @Id
    @Column(name = "user_id")
    private String userId;

    public UserInfo(String userId, String username, String password, Set<UserRole> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles=roles;

    }

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<UserRole> roles ;
}
