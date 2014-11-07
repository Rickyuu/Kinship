package com.speed.kinship.controller;

import java.util.Date;
import java.util.List;

import com.speed.kinship.model.Memory;
import com.speed.kinship.model.User;

public interface MemoryHandler {

	/**
	 * get all the memories of this user name
	 * @param username
	 * @return
	 */
	public List<Memory> getAllMemories(String username);
	
	/**
	 * add a new memory for this user
	 * @param user
	 * @param time
	 * @param content
	 * @return
	 */
	public Memory addMemory(User user, Date time, String content);
	
}
