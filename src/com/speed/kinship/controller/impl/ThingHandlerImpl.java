package com.speed.kinship.controller.impl;

import java.util.Date;
import java.util.List;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.model.Thing;
import com.speed.kinship.model.User;
import com.speed.kinship.net.Arguments;
import com.speed.kinship.net.Constants;
import com.speed.kinship.net.MessageHandler;
import com.speed.kinship.net.MethodMessage;

public class ThingHandlerImpl implements ThingHandler {

	@Override
	public List<Thing> getFirstNThings(String username, int n) {
		Arguments arguments = new Arguments();
		arguments.addArgument("username", username);
		arguments.addArgument("n", n);
		MethodMessage methodMessage = new MethodMessage(Constants.GET_FIRST_N_THINGS, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<Thing> result = (List<Thing>) resultObject;
		return result;
	}
	
	@Override
	public Thing addThing(User user, String title, Date time, String content, byte[] pic) {
		Arguments arguments = new Arguments();
		arguments.addArgument("user", user);
		arguments.addArgument("title", title);
		arguments.addArgument("time", time);
		arguments.addArgument("content", content);
		arguments.addArgument("pic", pic);
		MethodMessage methodMessage = new MethodMessage(Constants.ADD_THING, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		Thing result = (Thing) resultObject;
		return result;
	}

	@Override
	public List<Thing> getNextNThings(String username, Date startTime, int n) {
		Arguments arguments = new Arguments();
		arguments.addArgument("username", username);
		arguments.addArgument("startTime", startTime);
		arguments.addArgument("n", n);
		MethodMessage methodMessage = new MethodMessage(Constants.GET_NEXT_N_THINGS, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<Thing> result = (List<Thing>) resultObject;
		return result;
	}

}
