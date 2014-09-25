package com.justanymsg.mel.objectMappers;

import java.util.Date;

public class ScheduledMessage extends Message{

	private String messageRefId;
	private Date scheduledTime;
	
	public String getMessageRefId() {
		return messageRefId;
	}
	public void setMessageRefId(String messageRefId) {
		this.messageRefId = messageRefId;
	}
	public Date getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	
}
