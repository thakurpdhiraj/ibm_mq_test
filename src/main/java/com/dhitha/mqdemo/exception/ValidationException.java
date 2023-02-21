package com.dhitha.mqdemo.exception;

import java.util.List;

/**
 * @author Dhiraj
 */
public class ValidationException extends RuntimeException {

  private final String message;

  private final List<String> errors;

  public ValidationException(List<String> errors) {
    super(errors.toString());
    this.message = errors.toString();
    this.errors = errors;
  }

}
