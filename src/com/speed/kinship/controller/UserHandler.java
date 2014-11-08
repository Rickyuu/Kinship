package com.speed.kinship.controller;

import com.speed.kinship.model.Identity;
import com.speed.kinship.model.User;

public interface UserHandler {

	/**
	 * judge if the login legal or illegal
	 * if the user does not exist, will return null
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public User login(String username, String password, Identity identity); 
	
	/**
	 * judge if the register legal or illegal
	 * if the user name already be used, will return null
	 * remember each register will help you register two users, one for you, one for your parent or child
	 * the returned value will be you
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public User register(String username, String password, Identity identity);
	
}
