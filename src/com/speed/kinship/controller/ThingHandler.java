package com.speed.kinship.controller;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.util.Date;
import java.util.List;

import com.speed.kinship.model.Thing;
import com.speed.kinship.model.User;

public interface ThingHandler {

	/**
	 * get first n things of this user name
	 * this is necessary because we need to refresh sometimes
	 * if the total things are less than n, we just return the total things
	 * the things will be returned by time descent order
	 * @param username
	 * @param n
	 * @return
	 */
	public List<Thing> getFirstNThings(String username, int n);
	
	/**
	 * get next n things of this user name
	 * if the rest things are less than n, we just return all the rest things
	 * the things will be returned by time descent order
	 * @param username
	 * @param startTime the earliest time of thing you got already
	 * @param n the number of things you want to get
	 * @return
	 */
	public List<Thing> getNextNThings(String username, Date startTime, int n);
	
	/**
	 * add a new thing for this user
	 * @param user current user
	 * @param title the title of this thing
	 * @param time the time of this thing
	 * @param content the detailed description of this thing
	 * @param pic the picture of this thing
	 * @return
	 */
	public Thing addThing(User user, String title, Date time, String content, byte[] pic);
	
	/**
	 * delete a thing by id
	 * @param thingId
	 * @return
	 */
	public boolean deleteThing(int thingId);
	
}
