package com.speed.kinship.controller.impl;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import com.speed.kinship.controller.UserHandler;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.User;
import com.speed.kinship.net.Arguments;
import com.speed.kinship.net.Constants;
import com.speed.kinship.net.MessageHandler;
import com.speed.kinship.net.MethodMessage;

public class UserHandlerImpl implements UserHandler {

	@Override
	public User login(String username, String password, Identity identity) {
		Arguments arguments = new Arguments();
		arguments.addArgument("username", username);
		arguments.addArgument("password", password);
		arguments.addArgument("identity", identity);
		MethodMessage methodMessage = new MethodMessage(Constants.LOGIN, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		User result = (User) resultObject;
		return result;
	}

	@Override
	public User register(String username, String password, Identity identity) {
		Arguments arguments = new Arguments();
		arguments.addArgument("username", username);
		arguments.addArgument("password", password);
		arguments.addArgument("identity", identity);
		MethodMessage methodMessage = new MethodMessage(Constants.REGISTER, arguments);
		MessageHandler messageHandler = new MessageHandler();
		Object resultObject = messageHandler.handleMessage(methodMessage);
		if(resultObject == null) {
			return null;
		}
		User result = (User) resultObject;
		return result;
	}

}
