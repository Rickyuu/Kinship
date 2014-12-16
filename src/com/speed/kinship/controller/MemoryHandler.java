package com.speed.kinship.controller;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.util.Date;
import java.util.List;

import com.speed.kinship.model.Memory;
import com.speed.kinship.model.User;

public interface MemoryHandler {

	/**
	 * get all the memories of this user name
	 * the result will be returned with time descent order
	 * if there is no memory, a memory list of size 0 will be returned
	 * but please check both null value and list with size 0
	 * @param username
	 * @return
	 */
	public List<Memory> getAllMemories(String username);
	
	/**
	 * add a new memory for this user
	 * with this function, all the Year part of time will be set to 2014
	 * we think each date of a different year is just the same memorable date
	 * @param user current user
	 * @param time the time of the memorable date(only the part besides year will be remained)
	 * @param content the description of the memorable date
	 * @return
	 */
	public Memory addMemory(User user, Date time, String content);
	
	/**
	 * delete a memorable date by its id
	 * @param memoryId the id of the memorable date to be deleted
	 * @return
	 */
	public boolean deleteMemory(int memoryId);
	
}
