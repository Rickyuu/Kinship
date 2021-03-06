package com.speed.kinship.controller.impl;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.util.List;

import com.speed.kinship.controller.StateHandler;
import com.speed.kinship.model.Feedback;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;
import com.speed.kinship.net.Arguments;
import com.speed.kinship.net.Constants;
import com.speed.kinship.net.MessageHandler;
import com.speed.kinship.net.MethodMessage;

public class StateHandlerImpl implements StateHandler {

	@Override
	public List<State> getFirstNStates(String username, int n) {
		Arguments arguments = new Arguments();
		arguments.addArgument("username", username);
		arguments.addArgument("n", n);
		MethodMessage methodMessage = new MethodMessage(Constants.GET_FIRST_N_STATES, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<State> result = (List<State>) resultObject;
		return result;
	}

	@Override
	public List<State> getNextNStates(String username, int startId, int n) {
		Arguments arguments = new Arguments();
		arguments.addArgument("username", username);
		arguments.addArgument("startId", startId);
		arguments.addArgument("n", n);
		MethodMessage methodMessage = new MethodMessage(Constants.GET_NEXT_N_STATES, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<State> result = (List<State>) resultObject;
		return result;
	}

	@Override
	public State addState(User user, String content, byte[] pic) {
		Arguments arguments = new Arguments();
		arguments.addArgument("user", user);
		arguments.addArgument("content", content);
		arguments.addArgument("pic", pic);
		MethodMessage methodMessage = new MethodMessage(Constants.ADD_STATE, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		State result = (State) resultObject;
		return result;
	}

	@Override
	public Feedback addFeedback(int stateId, User feedbackCreator, String feedback) {
		Arguments arguments = new Arguments();
		arguments.addArgument("stateId", stateId);
		arguments.addArgument("feedbackCreator", feedbackCreator);
		arguments.addArgument("feedback", feedback);
		MethodMessage methodMessage = new MethodMessage(Constants.ADD_FEEDBACK, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		Feedback result = (Feedback) resultObject;
		return result;
	}

	@Override
	public boolean deleteState(int stateId) {
		Arguments arguments = new Arguments();
		arguments.addArgument("stateId", stateId);
		MethodMessage methodMessage = new MethodMessage(Constants.DELETE_STATE, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		boolean result = (boolean) resultObject;
		return result;
	}

	@Override
	public boolean deleteFeedback(int feedbackId) {
		Arguments arguments = new Arguments();
		arguments.addArgument("feedbackId", feedbackId);
		MethodMessage methodMessage = new MethodMessage(Constants.DELETE_FEEDBACK, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		boolean result = (boolean) resultObject;
		return result;
	}

}
