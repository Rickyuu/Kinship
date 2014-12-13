package com.speed.kinship.net;

import java.io.Serializable;

public class Constants implements Serializable {

	private static final long serialVersionUID = 450549763038272826L;
	// for socket connection, this value should be set with your own IP address value
	public static final String HOST_NAME = "143.89.174.164";
	public static final int PORT_NUM = 5181;
	
	// method name for user handler
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";
	// method name for state handler 
	public static final String GET_FIRST_N_STATES = "getFirstNStates";
	public static final String GET_NEXT_N_STATES = "getNextNStates";
	public static final String ADD_STATE = "addState";
	public static final String ADD_FEEDBACK = "addFeedback";
	public static final String DELETE_STATE = "deleteState";
	public static final String DELETE_FEEDBACK = "deleteFeedback";
	// method name for thing handler
	public static final String GET_FIRST_N_THINGS = "getFirstNThings";
	public static final String GET_NEXT_N_THINGS = "getNextNThings";
	public static final String ADD_THING = "addThing";
	public static final String DELETE_THING = "deleteThing";
	// method name for memory handler
	public static final String GET_ALL_MEMORIES = "getAllMemories";
	public static final String ADD_MEMORY = "addMemory";
	public static final String DELETE_MEMORY = "deleteMemory";
	
}
