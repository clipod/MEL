package com.justanymsg.mel.objectMappers;

public class Message {
	
	private String messageId;
	private Boolean isReceived;
	private MessageType messageType;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Boolean getIsReceived() {
		return isReceived;
	}
	public void setIsReceived(Boolean isReceived) {
		this.isReceived = isReceived;
	}
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	

}
