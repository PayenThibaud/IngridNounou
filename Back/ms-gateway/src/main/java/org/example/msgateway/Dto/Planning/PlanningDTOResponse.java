package org.example.msgateway.Dto.Planning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningDTOResponse {
    private int idHorraire;
    private LocalDate date;
    private LocalTime heureArriver;
    private LocalTime heureFin;
    private String idEnfant;
    private String nomEnfant;
}
