package com.speed.kinship.controller.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.speed.kinship.controller.MemoryHandler;
import com.speed.kinship.model.Memory;
import com.speed.kinship.model.User;
import com.speed.kinship.net.Arguments;
import com.speed.kinship.net.Constants;
import com.speed.kinship.net.MessageHandler;
import com.speed.kinship.net.MethodMessage;

public class MemoryHandlerImpl implements MemoryHandler {

	@Override
	public List<Memory> getAllMemories(String username) {
		Arguments arguments = new Arguments();
		arguments.addArgument("username", username);
		MethodMessage methodMessage = new MethodMessage(Constants.GET_ALL_MEMORIES, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<Memory> result = (List<Memory>) resultObject;
		return result;
	}

	@Override
	public Memory addMemory(User user, Date time, String content) {
		Arguments arguments = new Arguments();
		arguments.addArgument("user", user);
		arguments.addArgument("time", time);
		arguments.addArgument("content", content);
		MethodMessage methodMessage = new MethodMessage(Constants.ADD_MEMORY, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		Memory result = (Memory) resultObject;
		return result;
	}

	@Override
	public boolean deleteMemory(int memoryId) {
		Arguments arguments = new Arguments();
		arguments.addArgument("memoryId", memoryId);
		MethodMessage methodMessage = new MethodMessage(Constants.DELETE_MEMORY, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		boolean result = (boolean) resultObject;
		return result;
	}

}
