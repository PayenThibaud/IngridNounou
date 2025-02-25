package org.example.msgateway.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.msgateway.Dto.AuthDto.Login.LoginDtoRequest;
import org.example.msgateway.Dto.AuthDto.Login.LoginDtoResponse;
import org.example.msgateway.Dto.AuthDto.Register.RegisterDtoRequest;
import org.example.msgateway.Dto.AuthDto.Register.RegisterDtoResponse;
import org.example.msgateway.Dto.UserJwtDto.UserJwtDtoRequest;
import org.example.msgateway.Dto.UserJwtDto.UserJwtDtoResponse;
import org.example.msgateway.exception.AlreadyExistException;
import org.example.msgateway.exception.UserNotFoundException;
import org.example.msgateway.tools.RestClient;
import org.example.msgateway.utils.PortAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthentificationController {

    private ObjectMapper om;

    public AuthentificationController() {
        this.om = new ObjectMapper();
    }

    @PostMapping("/register")
    public ResponseEntity<LoginDtoResponse> register(@RequestBody RegisterDtoRequest registerDtoRequest) throws JsonProcessingException, AlreadyExistException, UserNotFoundException {
        RestClient<RegisterDtoResponse> registerRestClient = new RestClient<>("http://localhost:" + PortAPI.portUserJwt + "/api/auth/register");
        RegisterDtoResponse registerDtoResponse = registerRestClient.postRequest(om.writeValueAsString(registerDtoRequest), RegisterDtoResponse.class);
        if (registerDtoResponse.getId() != -1) {
            LoginDtoRequest loginDtoRequest = new LoginDtoRequest(registerDtoRequest.getEmail(), registerDtoRequest.getPassword());
            return loginMethod(loginDtoRequest);
        }
        throw new AlreadyExistException("User");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDtoResponse> login(@RequestBody LoginDtoRequest loginDtoRequest) throws JsonProcessingException, UserNotFoundException {
        return loginMethod(loginDtoRequest);
    }

    private ResponseEntity<LoginDtoResponse> loginMethod(LoginDtoRequest loginDtoRequest) throws JsonProcessingException, UserNotFoundException {
        RestClient<LoginDtoResponse> loginRestClient = new RestClient<>("http://localhost:" + PortAPI.portUserJwt + "/api/auth/login");
        LoginDtoResponse loginDtoResponse = loginRestClient.postRequest(om.writeValueAsString(loginDtoRequest), LoginDtoResponse.class);

        if (!loginDtoResponse.getToken().equals("NotFound")) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("token", loginDtoResponse.getToken());

            return new ResponseEntity<>(loginDtoResponse, HttpStatus.OK);
        }
        throw new UserNotFoundException();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("token");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserJwtDtoResponse> getUtilisateurById (@PathVariable int id){
        RestClient<UserJwtDtoResponse> utilisateurRestClient = new RestClient<>("http://localhost:"+ PortAPI.portUserJwt + "/api/auth/"+id);
        UserJwtDtoResponse utilisateurDtoResponse = utilisateurRestClient.getRequest(UserJwtDtoResponse.class);
        return new ResponseEntity<>(utilisateurDtoResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserJwtDtoResponse>> getAllUtilisateur() {
        // route du micro service utilisateur
        RestClient<UserJwtDtoResponse[]> utilisateurRestClient = new RestClient<>("http://localhost:" + PortAPI.portUserJwt + "/api/auth");
        // recuperation
        List<UserJwtDtoResponse> utilisateurDtoResponses = Arrays.stream(utilisateurRestClient.getRequest(UserJwtDtoResponse[].class)).toList();
        return new ResponseEntity<>(utilisateurDtoResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserJwtDtoResponse> updateUtilisateur(@PathVariable int id, @RequestBody UserJwtDtoRequest utilisateurDtoRequest) throws JsonProcessingException {
        RestClient<UserJwtDtoResponse> utilisateurRestClient = new RestClient<>("http://localhost:" + PortAPI.portUserJwt + "/api/auth/" + id);
        UserJwtDtoResponse utilisateurDtoResponse = utilisateurRestClient.putRequest(om.writeValueAsString(utilisateurDtoRequest), UserJwtDtoResponse.class);
        return new ResponseEntity<>(utilisateurDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUtilisateur(@PathVariable int id) {
        RestClient<Void> utilisateurRestClient = new RestClient<>("http://localhost:" + PortAPI.portUserJwt + "/api/auth/" + id);
        String responseMessage = "L'utilisateur avec l'ID " + id + " a été supprimé.";
        utilisateurRestClient.deleteRequest();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}