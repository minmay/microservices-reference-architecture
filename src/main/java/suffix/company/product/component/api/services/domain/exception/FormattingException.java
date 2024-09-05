package suffix.company.product.component.api.services.domain.exception;

public class FormattingException extends AbstractException {

	public FormattingException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
