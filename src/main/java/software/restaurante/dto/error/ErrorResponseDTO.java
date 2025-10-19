package software.restaurante.dto.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {

  private String message;

  private String errorCode;

}
