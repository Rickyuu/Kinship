package com.speed.kinship.net;

import java.io.Serializable;

public class MethodMessage implements Serializable {

	private static final long serialVersionUID = 2388822513641439611L;
	private String name;
	private Arguments arguments;
	
	public MethodMessage(String name, Arguments arguments) {
		this.name = name;
		this.arguments = arguments;
	}
	
	public String getName() {
		return name;
	}
	
	public Arguments getArguments() {
		return arguments;
	}
	
}
