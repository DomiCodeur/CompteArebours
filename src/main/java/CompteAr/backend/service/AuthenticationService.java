package CompteAr.backend.service;

import CompteAr.backend.model.*;
import CompteAr.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public UserInfo register(RegisterRequest request) {
    User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .signInMethod(request.getSignInMethod())
            .timeUnit(request.getTimeUnit())
            .role(Role.USER)
            .build();
    repository.save(user);
    String jwtToken = jwtService.generateToken(user);
    return UserInfo.builder()
            .id(user.getId())
            .email(user.getEmail())
            .timeUnit(user.getTimeUnit())
            .token(jwtToken)
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
    } catch (BadCredentialsException e) {
      throw new AuthenticationException("Email ou mot de passe incorrect");
    }

    User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new AuthenticationException("Email ou mot de passe incorrect"));
    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse(jwtToken, user.getId(), "");
  }

};

