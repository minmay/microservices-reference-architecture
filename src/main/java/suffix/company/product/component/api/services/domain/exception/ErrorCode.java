package suffix.company.product.component.api.services.domain.exception;

/**
 * Application errors.
 */
public enum ErrorCode {

	INTERNAL_ERROR(5000),
	URI_SYNTAX_ERROR(5001),
	DATA_INTEGRITY_ERROR(5004),
	BAD_REQUEST_ERROR(4000),
	NOT_FOUND_ERROR(4004),
	JSON_MARSHALLING_ERROR(4001),
	JSON_UNMARSHALLING_ERROR(4000),
	NOT_AUTHENTICATED(4010),
	NOT_AUTHORIZED(4030);

	private final int value;

	ErrorCode(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
