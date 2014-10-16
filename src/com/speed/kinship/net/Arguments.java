package com.speed.kinship.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Arguments implements Serializable {

	private static final long serialVersionUID = -1414104556779142550L;
	private Map<String, Object> params;
	
	public Arguments() {
		params = new HashMap<>();
	}
	
	public void addArgument(String name, Object value) {
		params.put(name, value);
	}
	
	public Object getArgument(String name) {
		return params.get(name);
	}
	
}
