package org.example.msplanning.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HorraireDtoReceive {

    private LocalDate date;
    private LocalTime heureArriver;
    private LocalTime heureFin;
    private String idEnfant;
}
