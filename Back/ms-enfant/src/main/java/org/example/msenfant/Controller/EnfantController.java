package org.example.msenfant.Controller;

import org.example.msenfant.Dto.EnfantDtoReceive;
import org.example.msenfant.Dto.EnfantDtoSend;
import org.example.msenfant.Service.EnfantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//###
//POST http://localhost:8082/enfant
//        Content-Type: application/json
//
//{
//    "prenom": "Emma",
//        "nom": "Martin",
//        "dateNaissance": "2019-06-12",
//        "fille": true
//}



@RestController
@RequestMapping("enfant")
public class EnfantController extends GeneriqueController<EnfantDtoReceive, EnfantDtoSend, EnfantService> {
    public EnfantController(EnfantService enfantService) {
        super(enfantService);
    }
}
