package software.restaurante.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import software.restaurante.domain.User;
import software.restaurante.execptions.UnAuthorizedException;
import software.restaurante.repository.UserRepository;
import software.restaurante.utils.enums.ErrorCode;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  public String authenticateByCredentials(String username, String password) {
    // Autenticar usando el AuthenticationManager
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );

    // Si la autenticación es exitosa, generar el token
    User user = userRepository.findByUsername(username)
        .orElseThrow(() ->
            new UnAuthorizedException("Usuario no encontrado", ErrorCode.UNAUTHORIZED)
        );

    return jwtService.generateToken(user);

  }


}
