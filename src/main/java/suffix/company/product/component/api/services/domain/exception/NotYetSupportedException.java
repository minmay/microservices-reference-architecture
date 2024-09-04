package suffix.company.product.component.api.services.domain.exception;

public class NotYetSupportedException extends AbstractException {

	public NotYetSupportedException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public NotYetSupportedException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
