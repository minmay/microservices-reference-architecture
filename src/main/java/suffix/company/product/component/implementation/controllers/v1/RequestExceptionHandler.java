package suffix.company.product.component.implementation.controllers.v1;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import suffix.company.product.component.api.services.domain.exception.AuthorizationException;
import suffix.company.product.component.api.services.domain.exception.BadRequestException;
import suffix.company.product.component.api.services.domain.exception.ErrorCode;
import suffix.company.product.component.api.services.domain.exception.InternalServerErrorException;
import suffix.company.product.component.api.services.domain.exception.NotFoundException;
import suffix.company.product.component.api.services.domain.exception.RemoteErrorException;
import suffix.company.product.component.implementation.controllers.v1.io.ErrorResponseDto;

import java.util.UUID;

/**
 * Handle all exceptions thrown inside controller methods.
 */
@Slf4j
@RestControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Default logic for handling exceptions.
	 */
	private ResponseEntity<ErrorResponseDto> handleExceptionDefaultLogic(Exception e, HttpServletRequest request, HttpStatus httpStatus, ErrorCode errorCode) {
		String requestId = (String) request.getAttribute("requestId");
		String errorId = UUID.randomUUID().toString();
		String method = request.getMethod();
		String path = request.getServletPath();
		String errorMessage = e.getMessage();

		log.error("Error while handling [{} {}], requestId=[{}]: errorId=[{}], errorCode=[{}], httpStatus=[{}], message=[{}]",
				method, path, requestId, errorId, errorCode.value(), httpStatus.value(), errorMessage, e);

		ErrorResponseDto errorResponse = new ErrorResponseDto(requestId, errorId, errorCode.value(), errorMessage);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}

	/**
	 * Handle any exceptions that were raised because a remote HTTP call returned with 4xx or 5xx status code.
	 */
	@ExceptionHandler(RemoteErrorException.class)
	public ResponseEntity<ErrorResponseDto> handleRemoteErrorException(RemoteErrorException e, HttpServletRequest request) {
		HttpStatus httpStatus = e.getHttpStatus();
		ErrorCode errorCode = e.getErrorCode();
		String requestId = (String) request.getAttribute("requestId");
		String errorId = UUID.randomUUID().toString();
		String method = request.getMethod();
		String path = request.getServletPath();
		String errorMessage = e.getMessage();
		String remoteResponseBody = e.getRemoteErrorPayload();

		log.error("Remote call returned without success while handling [{} {}], requestId=[{}]: errorId=[{}], errorCode=[{}], httpStatus=[{}], message=[{}], remoteResponseBody=[{}]",
				method, path, requestId, errorId, errorCode.value(), httpStatus.value(), errorMessage, remoteResponseBody, e);

		ErrorResponseDto errorResponse = new ErrorResponseDto(requestId, errorId, errorCode.value(), errorMessage, remoteResponseBody);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}

	/**
	 * Handle any exceptions that were raised because we received a bad request from the caller, for example missing or invalid parameters.
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorCode errorCode = e.getErrorCode();
		return handleExceptionDefaultLogic(e, request, httpStatus, errorCode);
	}

	/**
	 * Handle any exceptions that were raised because we received a not found from the caller, for example a database entity does not exist.
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		ErrorCode errorCode = e.getErrorCode();
		return handleExceptionDefaultLogic(e, request, httpStatus, errorCode);
	}

	/**
	 * Handle any exceptions that were raised because of an internal application error, such as loss of db connection.
	 */
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<ErrorResponseDto> handleInternalServerErrorException(InternalServerErrorException e, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorCode errorCode = e.getErrorCode();
		return handleExceptionDefaultLogic(e, request, httpStatus, errorCode);
	}

	/**
	 * Handle any exceptions that were raised because of unauthorized access.
	 */
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ErrorResponseDto> handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		ErrorCode errorCode = e.getErrorCode();
		return handleExceptionDefaultLogic(e, request, httpStatus, errorCode);
	}

	/**
	 * Handle any other exceptions that have not been covered above, by default these will be treated as Internal Server Errors.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception e, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
		return handleExceptionDefaultLogic(e, request, httpStatus, errorCode);
	}
}
