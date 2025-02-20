package org.example.msgateway.Dto.Enfant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnfantDTORequest {
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private boolean isFille;
}
