package suffix.company.product.component.implementation.controllers.v1.io;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Universal error payload returned by this application.
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

	/**
	 * Unique ID of the request that generated this error response.
	 */
	private String requestId;

	/**
	 * Unique ID for this error.
	 */
	private String errorId;

	/**
	 * Application error code.
	 */
	private Integer errorCode;

	/**
	 * Human readable error message.
	 */
	private String errorMessage;

	/**
	 * If remote call resulted in error response this would be that original error response.
	 */
	private String remoteErrorPayload;


	public ErrorResponseDto(String requestId, String errorId, Integer errorCode, String errorMessage) {
		this.errorId = errorId;
		this.requestId = requestId;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
