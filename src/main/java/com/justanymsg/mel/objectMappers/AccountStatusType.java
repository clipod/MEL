package com.justanymsg.mel.objectMappers;

public enum AccountStatusType {

	VERIFIED("VERIFIED"),CLOSED("CLOSED"),SUSPENDED("SUSPENDED"),UNVERIFIED("UNVERIFIED");

	private String accountStatus;

	private AccountStatusType(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountStatusType() {
		return accountStatus;
	}	
}
