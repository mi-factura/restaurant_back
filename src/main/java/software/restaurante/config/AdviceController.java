package software.restaurante.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import software.restaurante.dto.error.ErrorResponseDTO;
import software.restaurante.execptions.ApiException;
import software.restaurante.utils.enums.ErrorCode;

@ControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataFormatException(ApiException e) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorCode(e.getErrorCode().name());
        errorResponseDTO.setMessage(e.getMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedException(Exception e) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorCode(ErrorCode.INTERNAL_SERVER_ERROR.name());
        errorResponseDTO.setMessage("internal server error");

        log.error("Unexpected error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }


}
