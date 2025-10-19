package software.restaurante.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.restaurante.Service.AuthService;
import software.restaurante.dto.CredentialDTO;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class auth {

  private final AuthService authService;

  @PostMapping("/auth/login")
  public ResponseEntity<Void> login(@RequestBody CredentialDTO credentialDTO) {
    String token = authService.authenticateByCredentials(credentialDTO.getUsername(), credentialDTO.getPassword());
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
  }

}
