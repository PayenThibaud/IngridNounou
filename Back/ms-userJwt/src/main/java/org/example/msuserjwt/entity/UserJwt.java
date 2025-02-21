package org.example.msuserjwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msuserjwt.utils.enums.Role;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserJwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;

    @Column(unique = true)
    private String pseudo;
    private String email;
    private String password;
    private String tel;
    private Role role;
}
