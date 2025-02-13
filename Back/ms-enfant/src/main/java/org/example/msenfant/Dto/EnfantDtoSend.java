package org.example.msenfant.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EnfantDtoSend {
    private String idEnfant;

    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private boolean isFille;
}
