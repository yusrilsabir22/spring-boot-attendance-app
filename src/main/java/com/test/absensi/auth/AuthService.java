package com.test.absensi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.absensi.employee.EmployeeService;
import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.employee.Employee;
import com.test.absensi.employee.EmployeeRepository;
import com.test.absensi.token.Token;
import com.test.absensi.token.TokenRepository;
import com.test.absensi.token.TokenType;
import com.test.absensi.user.Profile;
import com.test.absensi.models.Request;
import com.test.absensi.config.jwt.JwtService;
import com.test.absensi.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthResponse login(Request.Login request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee employee = (Employee) authentication.getPrincipal();

        var info = AuthResponse.Info.build(employee);
        String token = jwtService.generateToken(employee);
        String refreshToken = jwtService.generateRefreshToken(employee);
        revokeAllUserTokens(employee);
        saveUserToken(employee, token);
        AuthResponse.Result result = AuthResponse.Result.builder()
                .info(info)
                .token(token)
                .refreshToken(refreshToken)
                .build();
        return new AuthResponse(result);
    }

    public String updatePassword(AuthChangePasswordRequest request) {
        var userSession = Utils.getUserSession();

        if(
                !Objects.equals(request.getOldPassword(), userSession.getPassword()) ||
                !Objects.equals(request.getNewPassword(), request.getNewPasswordConfirmation())
        ) {
            throw new UsernameNotFoundException("invalid email or password");
        }
        userSession.setPassword(request.getNewPasswordConfirmation());
        employeeRepository.save(userSession);
        return "success";
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var employee = employeeRepository.findByEmail(userEmail)
                    .orElseThrow();
            AuthResponse.Info info = AuthResponse.Info.build(employee);
            if (jwtService.isTokenValid(refreshToken, employee)) {
                var accessToken = jwtService.generateToken(employee);
                revokeAllUserTokens(employee);
                saveUserToken(employee, accessToken);
                var authResponse = AuthResponse.Result.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .info(info)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), new AuthResponse(authResponse));
            }
        }
    }

    private void saveUserToken(Employee employee, String token) {
        var jwtToken = Token.builder()
                .employee(employee)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(jwtToken);
    }

    private void revokeAllUserTokens(Employee employee) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(employee.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
