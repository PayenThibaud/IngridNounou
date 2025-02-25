package org.example.msgateway.Dto.UserJwtDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJwtDtoResponse {
    private int id_user;
    private String pseudo;
    private String email;
    private String password;
}