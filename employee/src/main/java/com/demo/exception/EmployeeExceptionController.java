package com.demo.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class EmployeeExceptionController extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = RecordNotfoundException.class)
	public ResponseEntity<Object> handleRecordNotFoundexception(RecordNotfoundException exception, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(exception.getMessage());
		details.add(exception.getParamName());
		details.add(exception.getParamValue());
        ErrorResponse error = new ErrorResponse("Invalid Search Criteria", details);
		return new ResponseEntity<Object>(error,HttpStatus.NOT_FOUND);
	}
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Invalid input", details);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
}


