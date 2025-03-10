package org.example.msuserjwt.DTO.Register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {
    private int id;
    private String pseudo;
    private String email;
    private String password;
    private int role;
}