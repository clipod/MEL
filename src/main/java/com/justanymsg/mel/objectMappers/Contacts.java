package com.justanymsg.mel.objectMappers;

import java.util.ArrayList;
import java.util.List;

public class Contacts {

	private List<Contact> contacts;

	public List<Contact> getContacts() {
		return (contacts == null)?new ArrayList<Contact>():contacts;
	}
}
