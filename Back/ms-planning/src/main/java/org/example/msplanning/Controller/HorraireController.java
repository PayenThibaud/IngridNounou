package org.example.msplanning.Controller;

import org.example.msplanning.Dto.HorraireDtoReceive;
import org.example.msplanning.Dto.HorraireDtoSend;
import org.example.msplanning.Service.HorraireService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//###
//POST http://localhost:8081/horraire
//Content-Type: application/json
//
//{
//    "date": "2025-02-11",
//    "heureArriver": "08:00",
//    "heureFin": "16:00",
//    "idEnfant": "John Doe",
//}


@RestController
@RequestMapping("horraire")
public class HorraireController extends GeneriqueController<HorraireDtoReceive, HorraireDtoSend, HorraireService> {
    public HorraireController(HorraireService service) {
        super(service);
    }
}

