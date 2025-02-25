package org.example.msuserjwt.DTO.UserJwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msuserjwt.utils.enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJwtDtoSend {
    private int id_user;
    private String pseudo;
    private String email;
    private String password;
    private Role role = Role.USER;
}