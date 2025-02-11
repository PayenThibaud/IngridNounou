package org.example.msplanning.Service;

import org.example.msplanning.Dto.HorraireDtoReceive;
import org.example.msplanning.Dto.HorraireDtoSend;
import org.example.msplanning.Entity.Horraire;
import org.example.msplanning.Repository.HorraireRepository;
import org.springframework.stereotype.Service;

@Service
public class HorraireService extends GeneriqueServiceImpl<HorraireDtoReceive, HorraireDtoSend, Horraire> {

    private final HorraireRepository horraireRepository;

    public HorraireService(HorraireRepository horraireRepository) {
        super(horraireRepository);
        this.horraireRepository = horraireRepository;
    }

    @Override
    protected HorraireDtoSend mapToResponse(Horraire horraire) {
        return HorraireDtoSend.builder()
                .idHorraire(horraire.getIdHorraire())
                .date(horraire.getDate())
                .heureArriver(horraire.getHeureArriver())
                .heureFin(horraire.getHeureFin())
                .idEnfant(horraire.getIdEnfant())
                .build();
    }

    @Override
    protected Horraire mapToEntity(HorraireDtoReceive dto) {
        return Horraire.builder()
                .date(dto.getDate())
                .heureArriver(dto.getHeureArriver())
                .heureFin(dto.getHeureFin())
                .idEnfant(dto.getIdEnfant())
                .build();
    }

    @Override
    public String getEntityName() {
        return "Horraire";
    }
}
