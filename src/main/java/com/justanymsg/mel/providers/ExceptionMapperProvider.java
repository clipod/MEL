package com.justanymsg.mel.providers;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.justanymsg.mel.execptions.MELException;
import com.justanymsg.mel.execptions.faults.BadRequest;
import com.justanymsg.mel.execptions.faults.MELFault;
import com.justanymsg.mel.execptions.faults.MethodNotAllowed;
import com.justanymsg.mel.execptions.faults.NotAcceptable;
import com.justanymsg.mel.execptions.faults.NotFound;
import com.justanymsg.mel.execptions.faults.Unauthorized;
import com.justanymsg.mel.execptions.faults.UnsupportedMediaType;

@Provider
@Component
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperProvider.class);

	private static final Map<Class<? extends Throwable>, MELException> ERRORS = new HashMap<Class<? extends Throwable>, MELException>();

	private static final Map<Integer, MELException> JERSEY_ERRORS = new HashMap<Integer, MELException>();

	static {
		ERRORS.put(javax.xml.bind.UnmarshalException.class, new MELException(new BadRequest(), "Invalid XML format."));
		ERRORS.put(java.io.EOFException.class, new MELException(new BadRequest(), "Invalid JSON format."));
		ERRORS.put(java.lang.NumberFormatException.class, new MELException(new BadRequest(), "Invalid number format."));

		// Workaround for a Jersey (JAX-RS) issue when he knows the XML input
		// but the method inputs expects another one.
		// (Example: send "method" to a "payments" POST)
		ERRORS.put(java.lang.IllegalArgumentException.class, new MELException(new BadRequest(), "Invalid input for the request."));

		// Jersey status codes
		JERSEY_ERRORS.put(SC_NOT_ACCEPTABLE, new MELException(new NotAcceptable()));
		JERSEY_ERRORS.put(SC_NOT_FOUND, new MELException(new NotFound(), "Resource not found."));
		JERSEY_ERRORS.put(SC_BAD_REQUEST, new MELException(new BadRequest()));
		JERSEY_ERRORS.put(SC_UNSUPPORTED_MEDIA_TYPE, new MELException(new UnsupportedMediaType()));
		JERSEY_ERRORS.put(SC_UNAUTHORIZED, new MELException(new Unauthorized()));
		JERSEY_ERRORS.put(SC_METHOD_NOT_ALLOWED, new MELException(new MethodNotAllowed()));
	}

	@Context
	private HttpServletRequest request;

	@Override
	public Response toResponse(Throwable throwable) {
		Throwable exception = throwable;
		exception = extractInnerException(exception);

		// Mapping error
		final MELException error = mapPaymentAPIFault(exception);
		final MELFault fault = error.getFault();

		// UUID detail
		final String uuid = UUID.randomUUID().toString();
		fault.setReferenceId(uuid);

		// Log the error
		LOGGER.error("Error details UUID: " + uuid, throwable);

		final String accept = request == null ? null : request.getHeader("Accept");
		final String type = getFailsafeContentType(accept);

		// Build the response
		return Response.status(error.getErrorCode()).entity(fault).type(type).build();
	}

	// Extract any special inner exception cause
	private Throwable extractInnerException(Throwable throwable) {
		Throwable exception = throwable;

		// Extract JAX-RS exception cause
		if (exception instanceof WebApplicationException && exception.getCause() != null) {
			exception = ((WebApplicationException) exception).getCause();
		}

		// Extract reflection exception cause
		if (exception instanceof InvocationTargetException) {
			exception = ((InvocationTargetException) exception).getCause();
		}

		return exception;
	}

	// Mapping generic error
	private MELException mapPaymentAPIFault(Throwable throwable) {
		// JAX-RS Errors
		final MELException jerseyError = mapJerseyException(throwable);
		if (jerseyError != null) {
			return jerseyError;
		}

		// Standard errors
		Throwable cause = throwable;
		while (cause != null) {
			if (cause instanceof MELException) {
				return (MELException) cause;
			}
			cause = cause.getCause();
		}

		return mapKnownErrors(throwable);
	}

	// Map known errors
	private MELException mapKnownErrors(Throwable throwable) {
		if (throwable != null && ERRORS.containsKey(throwable.getClass())) {
			return ERRORS.get(throwable.getClass());
		}
		// Default error
		return new MELException(throwable);
	}

	// Jersey errors
	private MELException mapJerseyException(Throwable exception) {
		if (exception instanceof WebApplicationException) {
			final WebApplicationException e = (WebApplicationException) exception;
			if (e.getResponse() != null) {
				return JERSEY_ERRORS.get(e.getResponse().getStatus());
			}
		}
		return null;
	}

	// Failsafe content type
	private static String getFailsafeContentType(String format) {
		return format != null && format.equalsIgnoreCase(MediaType.APPLICATION_JSON) ? MediaType.APPLICATION_JSON : MediaType.APPLICATION_XML;
	}

}