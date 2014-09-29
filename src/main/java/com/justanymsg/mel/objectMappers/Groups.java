package com.justanymsg.mel.objectMappers;

import java.util.ArrayList;
import java.util.List;

public class Groups {
	private List<Group> groups;

	public List<Group> getGroups() {
		return (groups == null) ? new ArrayList<Group>() : groups;
	}
}
