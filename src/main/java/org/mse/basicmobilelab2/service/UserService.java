package org.mse.basicmobilelab2.service;


import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.ERole;
import org.mse.basicmobilelab2.entity.Role;
import org.mse.basicmobilelab2.entity.User;
import org.mse.basicmobilelab2.exception.UserEmailDuplicationException;
import org.mse.basicmobilelab2.exception.UsernameDuplicatedException;
import org.mse.basicmobilelab2.payload.request.SignupRequest;
import org.mse.basicmobilelab2.repository.RoleRepository;
import org.mse.basicmobilelab2.repository.UserCollectionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class UserService {
    UserCollectionRepo userCollectionRepo;
    RoleRepository roleRepository;
    @Autowired
    public UserService(UserCollectionRepo userCollectionRepo, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userCollectionRepo = userCollectionRepo;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    PasswordEncoder passwordEncoder;


    public User enrollUser(SignupRequest signUpRequest){
        log.info(signUpRequest);

        if (userCollectionRepo.existsByUsername(signUpRequest.getUsername())) {
            log.info(signUpRequest.getUsername());
            throw new UsernameDuplicatedException("Error: Username is already taken!");
        }
        if (userCollectionRepo.existsByEmail(signUpRequest.getEmail())) {
            log.info(signUpRequest.getEmail());
            throw new UserEmailDuplicationException("Error: Email is already used");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getSchoolName());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userCollectionRepo.save(user);
        return user;
    }
    public User findUserByUsername(String username){
        return userCollectionRepo.findByUsername(username).get();
    }
}
