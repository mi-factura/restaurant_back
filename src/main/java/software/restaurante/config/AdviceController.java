package software.restaurante.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import software.restaurante.dto.error.ErrorResponseDTO;
import software.restaurante.execptions.ApiException;

@ControllerAdvice
public class AdviceController {

  @ExceptionHandler(value = ApiException.class)
  public ResponseEntity<ErrorResponseDTO> handleDataFormatException(ApiException e) {

    ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
    errorResponseDTO.setErrorCode(e.getErrorCode().name());
    errorResponseDTO.setMessage(e.getMessage());

    return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
  }









}
