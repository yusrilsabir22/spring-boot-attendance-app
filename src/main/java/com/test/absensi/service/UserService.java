package com.test.absensi.service;

import com.test.absensi.exceptions.EtAuthException;
import com.test.absensi.models.InitDataResponse;
import com.test.absensi.models.User;
import com.test.absensi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(InitDataResponse request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty()) {
            throw new EtAuthException("invalid email or password");
        }
        boolean isPasswordValid = Objects.equals(user.get().getPassword(), request.getPassword());
        if(!isPasswordValid) {
            throw new EtAuthException("invalid email or password");
        }
        return jwtService.generateToken(user.get());
    }

}