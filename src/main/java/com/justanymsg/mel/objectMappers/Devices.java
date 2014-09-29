package com.justanymsg.mel.objectMappers;

import java.util.ArrayList;
import java.util.List;

public class Devices {

	private List<Device> devices;

	public List<Device> getDevices() {
		return (devices == null) ? new ArrayList<Device>() : devices;
	}
}
