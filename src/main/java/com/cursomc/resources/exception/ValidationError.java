package com.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp, String excecao) {
		super(status, msg, timeStamp, excecao);
	}
	
	public void addError (String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
	
	public List<FieldMessage> getErrors() {
		return errors;
	}
}
