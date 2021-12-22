package com.lawencon.glexy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.glexy.exception.ValidationGlexyException;

public class BaseController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
		Map<String, Object> mapError = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		e.getBindingResult().getFieldErrors().forEach(err -> {
			errors.add(err.getDefaultMessage());
		});
		
		mapError.put("msg", errors);
		
		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NonUniqueResultException.class)
	public ResponseEntity<?> nonUniqueRes(NonUniqueResultException e){
		Map<String, Object> mapError = new HashMap<String, Object>();
		
		mapError.put("msg", e.getMessage());
		
		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> constrainViolation(ConstraintViolationException e){
		Map<String, Object> mapError = new HashMap<String, Object>();
		
		mapError.put("msg", e.getMessage());
		
		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<?> noResultException(NoResultException e){
		Map<String, Object> mapError = new HashMap<String, Object>();
		
		mapError.put("msg", e.getMessage());
		
		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
	}
  
	protected <T> T convertToModel(String src, Class<T> clazz ) throws Exception {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		return new ObjectMapper()
				.registerModule(javaTimeModule)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.readValue(src, clazz);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exception(Exception e){
		Map<String, Object> mapError = new HashMap<String, Object>();
		
		mapError.put("msg", NestedExceptionUtils.getRootCause(e).getMessage());
		
		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ValidationGlexyException.class)
	public ResponseEntity<?> exception(ValidationGlexyException e){
		Map<String, Object> mapError = new HashMap<String, Object>();
		
		mapError.put("msg", NestedExceptionUtils.getRootCause(e).getMessage());
		
		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
	}
}
