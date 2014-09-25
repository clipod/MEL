package com.justanymsg.mel.objectMappers;

import java.util.List;

public class Contact {

	private String accountId;
	private List<Message> messages;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}
