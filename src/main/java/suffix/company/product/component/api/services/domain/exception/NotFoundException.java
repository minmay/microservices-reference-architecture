package suffix.company.product.component.api.services.domain.exception;

public class NotFoundException extends AbstractException {

	public NotFoundException(String message) {
		super(ErrorCode.NOT_FOUND_ERROR, message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(ErrorCode.NOT_FOUND_ERROR, message, cause);
	}
}
