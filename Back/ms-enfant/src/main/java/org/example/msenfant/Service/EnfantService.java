package org.example.msenfant.Service;

import org.example.msenfant.Dto.EnfantDtoReceive;
import org.example.msenfant.Dto.EnfantDtoSend;
import org.example.msenfant.Entity.Enfant;
import org.example.msenfant.Repository.EnfantRepository;
import org.springframework.stereotype.Service;

@Service
public class EnfantService extends GeneriqueServiceImpl<EnfantDtoReceive, EnfantDtoSend, Enfant>{

    private final EnfantRepository repo;

    public EnfantService( EnfantRepository repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    protected EnfantDtoSend mapToResponse(Enfant enfant) {
        return EnfantDtoSend.builder()
                .idEnfant(enfant.getIdEnfant())
                .prenom(enfant.getPrenom())
                .nom(enfant.getNom())
                .dateNaissance(enfant.getDateNaissance())
                .isFille(enfant.isFille())
                .build();
    }

    @Override
    protected Enfant mapToEntity(EnfantDtoReceive dto) {
        String id = dto.getPrenom() + "_" + dto.getNom();

        int counter = 2;
        String idGenerer = id;
        while(repo.existsById(idGenerer)) {
            idGenerer = id + "_" + counter;
            counter++;
        }

        return Enfant.builder()
                .idEnfant(idGenerer)
                .prenom(dto.getPrenom())
                .nom(dto.getNom())
                .dateNaissance(dto.getDateNaissance())
                .isFille(dto.isFille())
                .build();
    }

    @Override
    public String getEntityName() {
        return "Enfant";
    }
}
