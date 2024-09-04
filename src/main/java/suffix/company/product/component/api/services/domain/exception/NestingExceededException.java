package suffix.company.product.component.api.services.domain.exception;

import lombok.Getter;

@Getter
public class NestingExceededException extends AbstractException {

	private final int depth;

	public NestingExceededException(ErrorCode errorCode, int depth) {
		super(errorCode);
		this.depth = depth;
	}
}
