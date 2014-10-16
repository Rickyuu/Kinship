package com.speed.kinship.controller;

import com.speed.kinship.model.Identity;

public interface UserHandler {

	/**
	 * judge if the login legal or illegal
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public boolean login(String username, String password, Identity identity); 
	
	/**
	 * judge if the register legal or illegal
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public boolean register(String username, String password, Identity identity);
	
}
