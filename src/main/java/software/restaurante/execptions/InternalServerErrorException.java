package software.restaurante.execptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import software.restaurante.utils.enums.ErrorCode;

@Getter
@Setter
public class InternalServerErrorException extends ApiException {

  public InternalServerErrorException(String message, ErrorCode errorCode) {
    super(message, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
