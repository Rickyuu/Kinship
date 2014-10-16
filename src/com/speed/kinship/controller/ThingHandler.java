package com.speed.kinship.controller;

import java.util.Date;
import java.util.List;

import com.speed.kinship.model.Pic;
import com.speed.kinship.model.Thing;
import com.speed.kinship.model.User;

public interface ThingHandler {

	/**
	 * get first n things of this user name
	 * this is necessary because we need to refresh sometimes
	 * if the total things are less than n, we just return the total things
	 * @param username
	 * @param n
	 * @return
	 */
	public List<Thing> getFirstNThings(String username, int n);
	
	/**
	 * get next n things of this user name
	 * if the rest things are less than n, we just return all the rest things
	 * @param username
	 * @param n
	 * @return
	 */
	public List<Thing> getNextNThings(String username, int n);
	
	/**
	 * add a new thing for this user
	 * @param user
	 * @param title
	 * @param time
	 * @param content
	 * @param pic
	 * @return
	 */
	public boolean addThing(User user, String title, Date time, String content, Pic pic);
	
}
