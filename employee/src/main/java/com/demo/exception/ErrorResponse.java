package com.demo.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> {

	  public ErrorResponse(String message, List<T> details) {
	        this.message = message;
	        this.details = details;
	    }
	 
	    //General error message about nature of error
	    private String message;
	 
	    public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<T> getDetails() {
			return details;
		}

		public void setDetails(List<T> details) {
			this.details = details;
		}

		//Specific errors in API request processing
	    private List<T> details;
}
