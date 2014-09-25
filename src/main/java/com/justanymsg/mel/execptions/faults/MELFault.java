package com.justanymsg.mel.execptions.faults;

import java.io.Serializable;

import org.boon.json.annotations.JsonIgnore;

public class MELFault implements Serializable, Cloneable {

	@JsonIgnore
	private static final long serialVersionUID = 1L;
	protected String referenceId;
	protected String message;
	protected Integer faultCode;

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(Integer faultCode) {
		this.faultCode = faultCode;
	}

}
