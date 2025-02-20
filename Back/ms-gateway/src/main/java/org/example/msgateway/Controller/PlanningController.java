package org.example.msgateway.Controller;

import org.example.msgateway.Controller.Generique.GeneriqueController;
import org.example.msgateway.Dto.Planning.PlanningDTORequest;
import org.example.msgateway.Dto.Planning.PlanningDTOResponse;
import org.example.msgateway.utils.PortAPI;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("planning")
public class PlanningController extends GeneriqueController<PlanningDTORequest, PlanningDTOResponse> {
    @Override
    protected String getBaseUrl() {
        return "http://localhost:" + PortAPI.portPlanning + "/horraire";
    }

    @Override
    protected String getEntityName() {
        return "Planning";
    }

    @Override
    protected Class<PlanningDTOResponse[]> getArrayResponseType() {
        return PlanningDTOResponse[].class;
    }

    @Override
    protected Class<PlanningDTOResponse> getSingleResponseType() {
        return PlanningDTOResponse.class;
    }
}
