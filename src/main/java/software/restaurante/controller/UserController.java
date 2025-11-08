package software.restaurante.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.restaurante.Service.JwtService;
import software.restaurante.Service.UsersService;
import software.restaurante.dto.UserDTO;

@RestController()
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class UserController {

  private final UsersService usersService;

  private final JwtService jwtService;

  @GetMapping("/users/profile")
  public ResponseEntity<UserDTO> getCurrentUserProfile(
      @RequestHeader("x-tiger-token") String token
  ) {

    String userid = jwtService.extractUserIdFromRawToken(token);

    UserDTO userDTO = usersService.getUserById(userid);

    return ResponseEntity.ok().body(userDTO);
  }

}
