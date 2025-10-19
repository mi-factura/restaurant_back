package software.restaurante.execptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import software.restaurante.utils.enums.ErrorCode;

@Getter
@Setter
public abstract class ApiException extends RuntimeException {

    private ErrorCode errorCode;

    private HttpStatus httpStatus;

  public ApiException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
    super(message);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
  }

}
