package suffix.company.product.component.api.services.domain.exception;

public class BadRequestException extends AbstractException {
	public BadRequestException(String message) {
		super(ErrorCode.BAD_REQUEST_ERROR, message);
	}

	protected BadRequestException(String message, Throwable cause) {
		super(ErrorCode.BAD_REQUEST_ERROR, message, cause);
	}
}
