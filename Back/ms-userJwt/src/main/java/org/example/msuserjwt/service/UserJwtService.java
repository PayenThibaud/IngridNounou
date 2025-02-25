package org.example.msuserjwt.service;

import org.example.msuserjwt.DTO.Register.RegisterRequestDto;
import org.example.msuserjwt.DTO.UserJwt.UserJwtDtoReceive;
import org.example.msuserjwt.DTO.UserJwt.UserJwtDtoSend;
import org.example.msuserjwt.Exceptions.UserAlreadyExistException;
import org.example.msuserjwt.Repository.UserJwtRepository;
import org.example.msuserjwt.entity.UserJwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserJwtService {

    private final UserJwtRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserJwtService(UserJwtRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserJwtDtoSend userAppMapperUtilisateurDTOSend(UserJwt utilisateur) {
        return UserJwtDtoSend.builder()
                .id_user(utilisateur.getId_user())
                .pseudo(utilisateur.getPseudo())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .role(utilisateur.getRole())
                .build();
    }

    private List<UserJwtDtoSend> listeUserAppMapperListeUtilisateurDTOSend(List<UserJwt> utilisateurs) {
        return utilisateurs.stream().map(this::userAppMapperUtilisateurDTOSend).toList();
    }

    public List<UserJwtDtoSend> getAll() {
        return listeUserAppMapperListeUtilisateurDTOSend((List<UserJwt>) userRepository.findAll());
    }

    public UserJwtDtoSend getById(int id) {
        return userRepository.findById(id)
                .map(this::userAppMapperUtilisateurDTOSend)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }



    public UserJwtDtoSend update(int id, UserJwtDtoReceive utilisateurDtoReceive) {
        UserJwt utilisateur = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        utilisateur.setPseudo(utilisateurDtoReceive.getPseudo());
        utilisateur.setEmail(utilisateurDtoReceive.getEmail());

        if (!utilisateurDtoReceive.getPassword().isEmpty() &&
                !passwordEncoder.matches(utilisateurDtoReceive.getPassword(), utilisateur.getPassword())) {
            utilisateur.setPassword(passwordEncoder.encode(utilisateurDtoReceive.getPassword()));
        }

        return userAppMapperUtilisateurDTOSend(userRepository.save(utilisateur));
    }

    public void delete(int id) {
        UserJwt utilisateur = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(utilisateur);
    }

    public UserJwt enregistrerUtilisateur(RegisterRequestDto registerRequestDto) throws UserAlreadyExistException {
        Optional<UserJwt> userAppOptional = userRepository.findByEmail(registerRequestDto.getEmail());
        if (userAppOptional.isPresent()) {
            throw new UserAlreadyExistException();
        }

        UserJwt user = new UserJwt(registerRequestDto.getPseudo(), registerRequestDto.getEmail(), registerRequestDto.getPassword(), registerRequestDto.getRole());
        return userRepository.save(user);
    }
}