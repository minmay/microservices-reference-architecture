package suffix.company.product.component.api.services.domain.exception;

public class AuthorizationException extends AbstractException {

	public AuthorizationException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
