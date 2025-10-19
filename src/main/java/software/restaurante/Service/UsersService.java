package software.restaurante.Service;

import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.restaurante.domain.User;
import software.restaurante.dto.UserDTO;
import software.restaurante.execptions.DataNotFoundException;
import software.restaurante.execptions.InternalServerErrorException;
import software.restaurante.repository.UserRepository;
import software.restaurante.utils.enums.ErrorCode;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

  private final UserRepository userRepository;

  public UserDTO getUserById(String userId) {

    UUID uuid = UUID.fromString(userId);

    try {
      Optional<User> user = userRepository.findById(uuid);
      if (!user.isPresent()) {
        throw new DataNotFoundException("User not found", ErrorCode.DATA_NOT_FOUND);
      }

      return UserDTO.fromEntity(user.get());

    } catch (Exception e) {
      log.error("Error getting user profile", e);
      throw new InternalServerErrorException("Error getting user profile", ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }

}
