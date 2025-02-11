package org.example.msplanning.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Horraire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHorraire;

    private LocalDate date;
    private LocalTime heureArriver;
    private LocalTime heureFin;

    private String idEnfant;
}
