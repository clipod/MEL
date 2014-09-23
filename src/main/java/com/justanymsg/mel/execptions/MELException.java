package com.justanymsg.mel.execptions;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

import com.justanymsg.mel.execptions.faults.BadRequest;
import com.justanymsg.mel.execptions.faults.InternalServerError;
import com.justanymsg.mel.execptions.faults.MELFault;
import com.justanymsg.mel.execptions.faults.MethodNotAllowed;
import com.justanymsg.mel.execptions.faults.NotAcceptable;
import com.justanymsg.mel.execptions.faults.NotFound;
import com.justanymsg.mel.execptions.faults.Unauthorized;
import com.justanymsg.mel.execptions.faults.UnsupportedMediaType;

@SuppressWarnings("serial")
public class MELException extends RuntimeException implements Cloneable {

	private static final Map<Class<? extends MELFault>, Integer> ERROR_CODES = new HashMap<Class<? extends MELFault>, Integer>();

	static {
		ERROR_CODES.put(Unauthorized.class, SC_UNAUTHORIZED);
		ERROR_CODES.put(BadRequest.class, SC_BAD_REQUEST);
		ERROR_CODES.put(NotFound.class, SC_NOT_FOUND);
		ERROR_CODES.put(NotAcceptable.class, SC_NOT_ACCEPTABLE);
		ERROR_CODES.put(UnsupportedMediaType.class, SC_UNSUPPORTED_MEDIA_TYPE);
		ERROR_CODES.put(InternalServerError.class, SC_INTERNAL_SERVER_ERROR);
		ERROR_CODES.put(MethodNotAllowed.class, SC_METHOD_NOT_ALLOWED);
	}

	private final MELFault fault;

	private final int errorCode;

	public MELException() {
		this("Internal error has occurred.");
	}

	public MELException(String message) {
		this(new InternalServerError(), message);
	}

	public MELException(Throwable throwable) {
		this("Unexpected error has occurred.", throwable);
	}

	public MELException(String message, Throwable throwable) {
		this(new InternalServerError(), message, throwable);
	}

	public MELException(MELFault fault) {
		this(fault, null, ERROR_CODES.get(fault.getClass()), null);
	}

	public MELException(MELFault fault, String message) {
		this(fault, message, ERROR_CODES.get(fault.getClass()), null);
	}

	public MELException(MELFault fault, String message, Throwable throwable) {
		this(fault, message, ERROR_CODES.get(fault.getClass()), throwable);
	}
	private MELException(MELFault fault, String message, int errorCode, Throwable throwable) {
		super(message, throwable);
		fault.setMessage(message);
		this.fault = fault;
		this.errorCode = errorCode;
	}

	public MELFault getFault() {
		return (MELFault) SerializationUtils.clone(fault);
	}

	public int getErrorCode() {
		return errorCode;
	}
}
