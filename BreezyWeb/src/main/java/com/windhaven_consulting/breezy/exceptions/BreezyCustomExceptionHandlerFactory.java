package com.windhaven_consulting.breezy.exceptions;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class BreezyCustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	 
	  private ExceptionHandlerFactory parent;
	 
	  public BreezyCustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
	    this.parent = parent;
	  }
	 
	  @Override
	  public ExceptionHandler getExceptionHandler() {
	    ExceptionHandler result = new BreezyCustomExceptionHandler(parent.getExceptionHandler());
	    return result;
	  }
}
