package org.example.msuserjwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserJwtService {


        private final UserJwtRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserAppService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        private UtilisateurDtoSend userAppMapperUtilisateurDTOSend(UserJwt utilisateur) {
            return UtilisateurDtoSend.builder()
                    .id_utilisateur(utilisateur.getId_user())
                    .pseudo(utilisateur.getPseudo())
                    .email(utilisateur.getEmail())
                    .password(utilisateur.getPassword())
                    .role(utilisateur.getRole())
                    .build();
        }

        private List<UtilisateurDtoSend> listeUserAppMapperListeUtilisateurDTOSend(List<UserApp> utilisateurs) {
            return utilisateurs.stream().map(this::userAppMapperUtilisateurDTOSend).toList();
        }

        public List<UtilisateurDtoSend> getAll() {
            return listeUserAppMapperListeUtilisateurDTOSend((List<UserApp>) userRepository.findAll());
        }

        public UtilisateurDtoSend getById(int id) {
            return userRepository.findById(id)
                    .map(this::userAppMapperUtilisateurDTOSend)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }



        public UtilisateurDtoSend update(int id, UtilisateurDtoReceive utilisateurDtoReceive) {
            UserApp utilisateur = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

            utilisateur.setPseudo(utilisateurDtoReceive.getPseudo());
            utilisateur.setEmail(utilisateurDtoReceive.getEmail());

            if (!utilisateurDtoReceive.getPassword().isEmpty() &&
                    !passwordEncoder.matches(utilisateurDtoReceive.getPassword(), utilisateur.getPassword())) {
                utilisateur.setPassword(passwordEncoder.encode(utilisateurDtoReceive.getPassword()));
            }

            return userAppMapperUtilisateurDTOSend(userRepository.save(utilisateur));
        }

        public void delete(int id) {
            UserApp utilisateur = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.delete(utilisateur);
        }

        public UserApp enregistrerUtilisateur(RegisterRequestDto registerRequestDto) throws UserAlreadyExistException {
            Optional<UserApp> userAppOptional = userRepository.findByEmail(registerRequestDto.getEmail());
            if (userAppOptional.isPresent()) {
                throw new UserAlreadyExistException();
            }

            UserApp user = new UserApp(registerRequestDto.getPseudo(), registerRequestDto.getEmail(), registerRequestDto.getPassword(), registerRequestDto.getRole());
            return userRepository.save(user);
        }
    }
