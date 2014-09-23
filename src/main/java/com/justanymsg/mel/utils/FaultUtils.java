package com.justanymsg.mel.utils;

import com.justanymsg.mel.execptions.faults.MELFault;

public class FaultUtils {

	public static MELFault createfault(MELFault faultType, String message, Integer faultCode) {
		faultType.setFaultCode(faultCode);
		faultType.setMessage(message);
		return faultType;
	}
}
