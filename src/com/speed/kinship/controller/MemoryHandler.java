package com.speed.kinship.controller;

import java.util.Date;
import java.util.List;

import com.speed.kinship.model.Memory;
import com.speed.kinship.model.User;

public interface MemoryHandler {

	/**
	 * get first n memories of this user name
	 * this is necessary because we need to refresh sometimes
	 * if the total memories are less than n, we just return the total memories
	 * @param username
	 * @param n
	 * @return
	 */
	public List<Memory> getFirstNMemories(String username, int n);
	
	/**
	 * get next n memories of this user name
	 * if the rest memories are less than n, we just return all the rest memories
	 * @param username
	 * @param startId
	 * @param n
	 * @return
	 */
	public List<Memory> getNextNMemories(String username, int startId, int n);
	
	/**
	 * add a new memory for this user
	 * @param user
	 * @param time
	 * @param content
	 * @return
	 */
	public Memory addMemory(User user, Date time, String content);
	
}
