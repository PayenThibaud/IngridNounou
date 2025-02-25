package org.example.msgateway.Dto.UserJwtDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJwtDtoRequest {
    private String pseudo;
    private String email;
    private String password;
}