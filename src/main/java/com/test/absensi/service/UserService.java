package com.test.absensi.service;

import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.db.models.User;
import com.test.absensi.models.Profile;
import com.test.absensi.models.Request;
import com.test.absensi.repositories.UserRepository;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(Request.Login request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty()) {
            throw new BadRequest("invalid email or password");
        }
        boolean isPasswordValid = Objects.equals(user.get().getPassword(), request.getPassword());
        if(!isPasswordValid) {
            throw new BadRequest("invalid email or password");
        }
        return jwtService.generateToken(user.get());
    }

    public User create(String email, String password, Profile profile) throws DuplicateException {
        var currentUser = Utils.getUserSession();
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            throw new DuplicateException("User already exist");
        }
        var userSession = Utils.getUserSession();

        User newUser = new User(
                email,
                password,
                profile,
                userSession.getPerusahaan()
        );
        return userRepository.save(newUser);
    }

}
