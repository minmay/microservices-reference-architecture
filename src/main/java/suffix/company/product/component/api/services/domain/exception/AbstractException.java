package suffix.company.product.component.api.services.domain.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {

	private final ErrorCode errorCode;

	protected AbstractException(ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	protected AbstractException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	protected AbstractException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
