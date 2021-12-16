package com.lawencon.glexy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
	
	protected <T> T convertToModel(String src, Class<T> clazz ) throws Exception {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		return new ObjectMapper()
				.registerModule(javaTimeModule)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.readValue(src, clazz);
	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<?> nonUniqueRes(NonUniqueResultException e){
//		Map<String, Object> mapError = new HashMap<String, Object>();
//		
//		mapError.put("msg", e.getMessage());
//		
//		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<?> constrainViolation(ConstraintViolationException e){
//		Map<String, Object> mapError = new HashMap<String, Object>();
//		
//		mapError.put("msg", e.getMessage());
//		
//		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<?> noResultException(NoResultException e){
//		Map<String, Object> mapError = new HashMap<String, Object>();
//		
//		mapError.put("msg", e.getMessage());
//		
//		return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
//	}
}
