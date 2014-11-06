package com.speed.kinship.controller;

import java.util.Date;
import java.util.List;

import com.speed.kinship.model.Feedback;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;

public interface StateHandler {

	/**
	 * get first n states of this user name
	 * this is necessary because we need to refresh sometimes
	 * if the total states are less than n, we just return the total states
	 * @param username
	 * @param n
	 * @return
	 */
	public List<State> getFirstNStates(String username, int n);
	
	/**
	 * get next n states of this user name
	 * if the rest states are less than n, we just return all the rest states
	 * @param username
	 * @param startId
	 * @param n
	 * @return
	 */
	public List<State> getNextNStates(String username, int startId, int n);
	
	/**
	 * add a new state for this user
	 * @param user
	 * @param time
	 * @param content
	 * @param pic
	 * @return
	 */
	public State addState(User user, Date time, String content, byte[] pic);
	
	/**
	 * add a feedback for the state
	 * @param stateId
	 * @param feedbackCreator
	 * @param feedback
	 * @return
	 */
	public Feedback addFeedback(int stateId, User feedbackCreator, String feedback);
	
}
