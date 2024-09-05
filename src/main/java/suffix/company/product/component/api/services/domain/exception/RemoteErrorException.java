package suffix.company.product.component.api.services.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * We throw this exception when response from a remote HTTP call returns with 4xx or 5xx status code. This exception
 * captures original response status code, headers, and body so they can be used later in error handling.
 */
@Getter
public class RemoteErrorException extends AbstractException {

	private final HttpStatus httpStatus;
	private final String remoteErrorPayload;
	private final HttpHeaders httpHeaders;


	public RemoteErrorException(HttpStatus httpStatus, HttpHeaders httpHeaders, String remoteErrorPayload, ErrorCode errorCode, String message) {
		super(errorCode, message);
		this.httpStatus = httpStatus;
		this.httpHeaders = httpHeaders;
		this.remoteErrorPayload = remoteErrorPayload;
	}
}
