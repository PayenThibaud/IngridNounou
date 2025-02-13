package org.example.msplanning.Service;

import org.example.msplanning.Dto.EnfantDtoSend;
import org.example.msplanning.Dto.HorraireDtoReceive;
import org.example.msplanning.Dto.HorraireDtoSend;
import org.example.msplanning.Entity.Horraire;
import org.example.msplanning.Repository.HorraireRepository;
import org.example.msplanning.RestClient.RestClient;
import org.springframework.stereotype.Service;

@Service
public class HorraireService extends GeneriqueServiceImpl<HorraireDtoReceive, HorraireDtoSend, Horraire> {

    private final HorraireRepository horraireRepository;

    public HorraireService(HorraireRepository horraireRepository) {
        super(horraireRepository);
        this.horraireRepository = horraireRepository;
    }

    private void rechercherNomEnfant(HorraireDtoSend horraireDtoSend) {
        RestClient<EnfantDtoSend> enfantRestClient = new RestClient<>("http://localhost:8082/enfant/" + horraireDtoSend.getIdEnfant());

        try {
            EnfantDtoSend enfantDtoResponse = enfantRestClient.getRequest(EnfantDtoSend.class);
            horraireDtoSend.setNomEnfant(enfantDtoResponse.getNom());
        } catch (Exception e) {
            System.err.println("Erreur, du service enfant pour l horraire avec l'ID " + horraireDtoSend.getIdEnfant());
            e.printStackTrace();
            horraireDtoSend.setNomEnfant("Nom indisponible");
        }
    }


    @Override
    protected HorraireDtoSend mapToResponse(Horraire horraire) {
        HorraireDtoSend horraireDtoSend = HorraireDtoSend.builder()
                .idHorraire(horraire.getIdHorraire())
                .date(horraire.getDate())
                .heureArriver(horraire.getHeureArriver())
                .heureFin(horraire.getHeureFin())
                .idEnfant(horraire.getIdEnfant())
                .build();

        rechercherNomEnfant(horraireDtoSend);

        return horraireDtoSend;
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
