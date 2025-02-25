package org.example.msgateway.Controller.loginController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.msgateway.Dto.Enfant.EnfantDTORequest;
import org.example.msgateway.Dto.Enfant.EnfantDTOResponse;
import org.example.msgateway.tools.RestClient;
import org.example.msgateway.utils.PortAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("enfant")
public class EnfantController {

    private ObjectMapper objectMapper;

    public EnfantController() {
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public ResponseEntity<List<EnfantDTOResponse>> getAllEnfant() {
        RestClient<EnfantDTOResponse[]> enfantRestClient = new RestClient<>("http://localhost:" + PortAPI.portEnfant + "/enfant");
        List<EnfantDTOResponse> responses = Arrays.stream(enfantRestClient.getRequest(EnfantDTOResponse[].class)).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnfantDTOResponse> getEnfantById(@PathVariable String id){
        RestClient<EnfantDTOResponse> enfantRestClient = new RestClient<>("http://localhost:" + PortAPI.portEnfant + "/enfant/" + id);
        EnfantDTOResponse enfantDtoResponse = enfantRestClient.getRequest(EnfantDTOResponse.class);
        return new ResponseEntity<>(enfantDtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnfantDTOResponse> postEnfant(@RequestBody EnfantDTORequest enfantDtoRequest) throws JsonProcessingException {
        RestClient<EnfantDTOResponse> enfantRestClient = new RestClient<>("http://localhost:" + PortAPI.portEnfant + "/enfant");
        EnfantDTOResponse enfantDtoResponse = enfantRestClient.postRequest(objectMapper.writeValueAsString(enfantDtoRequest), EnfantDTOResponse.class);
        return new ResponseEntity<>(enfantDtoResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnfantDTOResponse> updateEnfant(@PathVariable String id, @RequestBody EnfantDTORequest enfantDtoRequest) throws JsonProcessingException {
        RestClient<EnfantDTOResponse> enfantRestClient = new RestClient<>("http://localhost:" + PortAPI.portEnfant + "/enfant/" + id);
        EnfantDTOResponse enfantDtoResponse = enfantRestClient.putRequest(objectMapper.writeValueAsString(enfantDtoRequest), EnfantDTOResponse.class);
        return new ResponseEntity<>(enfantDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnfant(@PathVariable String id) {
        RestClient<Void> enfantRestClient = new RestClient<>("http://localhost:" + PortAPI.portEnfant + "/enfant/" + id);
        String responseMessage = "L'enfant avec l'ID " + id + " a été supprimé.";
        enfantRestClient.deleteRequest();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
