package com.windhaven_consulting.breezy.exceptions;

import java.util.Iterator;
import java.util.UUID;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreezyCustomExceptionHandler extends ExceptionHandlerWrapper {
	static final Logger LOG = LoggerFactory.getLogger(BreezyCustomExceptionHandler.class);

	private ExceptionHandler wrapped;

	public BreezyCustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable throwable = context.getException();
			boolean hasBreezyApplicationException = false;
			
			while(!hasBreezyApplicationException && throwable.getCause() != null) {
				throwable = throwable.getCause();
				
				if(throwable instanceof BreezyApplicationException) {
					hasBreezyApplicationException = true;
					LOG.debug("Exception message is: " + throwable.getMessage());
				}
			}
			
			LOG.error("Id:" + UUID.randomUUID() + ", exception thrown. Cause = " + throwable.getMessage() + ".  Stacktrace follows.", throwable);
			
			FacesContext fc = FacesContext.getCurrentInstance();

			try {
				Flash flash = fc.getExternalContext().getFlash();

				// Put the exception in the flash scope to be displayed in the
				// error
				// page if necessary ...
				
				flash.put("message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "summary message", "detail"));

				System.out.println("the error is put in the flash: " + throwable.getMessage());

				NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
				navigationHandler.handleNavigation(fc, null, "error?faces-redirect=true");

				fc.renderResponse();
			} finally {
				iterator.remove();
			}
		}

		// Let the parent handle the rest
		getWrapped().handle();
	}

}
