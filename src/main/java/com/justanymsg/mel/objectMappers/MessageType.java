package com.justanymsg.mel.objectMappers;

public enum MessageType {
	
	TEXT("TEXT"), PHOTO("PHOTO"), VIDEO("VIDEO"), AUDIO("AUDIO");

	private String messageType;

	private MessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}
}
