package org.example.msuserjwt.Controller;

import org.example.msuserjwt.DTO.Login.LoginRequestDto;
import org.example.msuserjwt.DTO.Login.LoginResponseDto;
import org.example.msuserjwt.DTO.Register.RegisterRequestDto;
import org.example.msuserjwt.DTO.Register.RegisterResponseDto;
import org.example.msuserjwt.DTO.UserJwt.UserJwtDtoReceive;
import org.example.msuserjwt.DTO.UserJwt.UserJwtDtoSend;
import org.example.msuserjwt.Exceptions.UserAlreadyExistException;
import org.example.msuserjwt.Jwt.JWTGenerator;
import org.example.msuserjwt.entity.UserJwt;
import org.example.msuserjwt.service.UserJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserJwtController {

    private final AuthenticationManager authenticationManager;
    private final UserJwtService userAppService;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator generator;

    @Autowired
    public UserJwtController(AuthenticationManager authenticationManager, UserJwtService userAppService, PasswordEncoder passwordEncoder, JWTGenerator generator) {
        this.authenticationManager = authenticationManager;
        this.userAppService = userAppService;
        this.passwordEncoder = passwordEncoder;
        this.generator = generator;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = generator.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDTO) throws UserAlreadyExistException {
        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        UserJwt userApp = userAppService.enregistrerUtilisateur(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new RegisterResponseDto(userApp.getId_user(), userApp.getPseudo(), userApp.getEmail(), userApp.getPassword(), userApp.getRole().ordinal())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserJwtDtoSend> getById(@PathVariable int id) {
        return ResponseEntity.ok(userAppService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserJwtDtoSend>> getAll() {
        return ResponseEntity.ok(userAppService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserJwtDtoSend> update(@PathVariable int id, @RequestBody UserJwtDtoReceive utilisateurDtoReceive) {
        return ResponseEntity.ok(userAppService.update(id, utilisateurDtoReceive));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        userAppService.delete(id);
        return ResponseEntity.ok("{\"message\": \"Utilisateur supprimé avec succès\"}");
    }

}