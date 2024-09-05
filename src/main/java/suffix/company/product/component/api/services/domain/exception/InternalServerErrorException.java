package suffix.company.product.component.api.services.domain.exception;

public class InternalServerErrorException extends AbstractException {

	public InternalServerErrorException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public InternalServerErrorException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
