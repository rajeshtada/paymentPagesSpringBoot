package com.ftk.pg.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ftk.pg.dto.ResponseWrapper;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseWrapper<String>> handleResourceNotFound(ResourceNotFoundException ex) {
		logger.info("ResourceNotFoundException");
		customException(ex);

//		ResponseWrapper<String> response = new ResponseWrapper<>(ex.getMessage(), null);
		ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(),ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ResponseWrapper<String>> handleValidationException(ValidationException ex) {
		logger.info("ValidationException");
		customException(ex);

//		ResponseWrapper<String> response = new ResponseWrapper<>(ex.getMessage(), null);
		ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(SomethingWentWrongException.class)
	public ResponseEntity<ResponseWrapper<String>> handleGenericException(SomethingWentWrongException ex) {
		logger.info("SomethingWentWrongException");
		customException(ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage(), null));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		logger.info("MethodArgumentNotValidException");
		customException(ex);
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<ResponseWrapper<String>> handleConstraintViolationException(ConstraintViolationException ex) {
//		customException(ex);
//
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(new ResponseWrapper<>(ex.getMessage(), null));
//	}
//	
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public ResponseEntity<ResponseWrapper<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//		customException(ex);
//
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(new ResponseWrapper<>(ex.getMessage(), null));
//	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseWrapper<String>> handleConstraintViolationException(ConstraintViolationException ex) {
		logger.info("ConstraintViolationException");
		customException(ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage(), null));
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseWrapper<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		logger.info("ConstraintViolationException");
		customException(ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage(), null));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseWrapper<String>> handleGenericException(Exception ex) {
		logger.info("Exception");
		customException(ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"INTERNAL_SERVER_ERROR", null));
	}

//	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
//	@ResponseBody
//	public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException() {
//		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//				.body("Acceptable MIME type: " + MediaType.APPLICATION_JSON_VALUE);
//	}

	public void customException(Exception e) {
		StackTraceElement[] arr = e.getStackTrace();
		logger.error(e.getMessage());
		logger.error(e.getCause());
		for (StackTraceElement s : arr) {
			if (s.getClassName().contains("com.ftk")) {
				logger.error("==> " + s.getClassName() + " : " + s.getMethodName() + " : " + s.getLineNumber());
			}
		}
	}

	@ExceptionHandler(InvalidAuthKeyException.class)
	public ResponseEntity<ResponseWrapper<String>> handleTokenAuthKey(InvalidAuthKeyException ex) {
		logger.info("Exception : InvalidAuthKeyException");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage(), null));
	}
	
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseWrapper<Map>> handleNoHandlerFound(NoHandlerFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 404);
        response.put("error", "Not Found");
        response.put("message", "The endpoint " + ex.getHttpMethod() + " " + ex.getRequestURL() + " does not exist.");
        response.put("timestamp", System.currentTimeMillis());
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseWrapper<Map>(HttpStatus.NOT_FOUND.value(),"Invalid Url", response));
    }

	
}
