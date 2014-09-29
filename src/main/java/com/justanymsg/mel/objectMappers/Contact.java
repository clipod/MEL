package com.justanymsg.mel.objectMappers;

import java.util.List;

public class Contact {

	private String accountId;
	//Had to add this field because searching of users and adding them will create spam.
	private Boolean isVerified;
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
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	
}
