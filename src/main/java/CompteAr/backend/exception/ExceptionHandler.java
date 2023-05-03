package compteAr.backend.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import compteAr.backend.resources.ExceptionResource;

/**
 * ControllerAdvice permettant d'intercepter les exceptions qui surviennent au
 * niveau des {@link Controller}
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Interception des exceptions provenant d'un défaut de validation des données.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ExceptionResource exceptionResource = ExceptionResource.builder().date(new Date())
				.description("Data validation failed").message(ex.getBindingResult().toString()).build();
		return new ResponseEntity(exceptionResource, HttpStatus.BAD_REQUEST);
	}
}
