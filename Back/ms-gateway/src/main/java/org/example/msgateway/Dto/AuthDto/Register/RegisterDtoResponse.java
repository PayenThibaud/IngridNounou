package org.example.msgateway.Dto.AuthDto.Register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDtoResponse {
    private int id;
    private String pseudo;
    private String email;
    private String password;
    private int role;
}