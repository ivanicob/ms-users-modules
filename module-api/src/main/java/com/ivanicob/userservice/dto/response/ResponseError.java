package com.ivanicob.userservice.dto.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
public class ResponseError {
	
	@NotNull(message="Timestamp is required")
	private LocalDateTime timestamp;
	
	@NotNull(message="Details is required")
    private String details;

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}