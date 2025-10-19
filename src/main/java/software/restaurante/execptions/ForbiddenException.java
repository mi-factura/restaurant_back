package software.restaurante.execptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import software.restaurante.utils.enums.ErrorCode;

@Getter
@Setter
public class ForbiddenException extends ApiException {

  public ForbiddenException(String message, ErrorCode errorCode) {
    super(message, errorCode, HttpStatus.FORBIDDEN);
  }
}
