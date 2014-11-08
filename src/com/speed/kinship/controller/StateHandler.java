package com.speed.kinship.controller;

import java.util.List;

import com.speed.kinship.model.Feedback;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;

public interface StateHandler {

	/**
	 * get first n states of this user name
	 * this is necessary because we need to refresh sometimes
	 * if the total states are less than n, we just return the total states
	 * the states will be returned by time descent order
	 * @param username
	 * @param n the number of states you want to be returned
	 * @return
	 */
	public List<State> getFirstNStates(String username, int n);
	
	/**
	 * get next n states of this user name
	 * if the rest states are less than n, we just return all the rest states
	 * if you use ("ricky", 253, 5), you may get states of following id: 252, 250, 237, 220, 218
	 * @param username
	 * @param startId the smallest id of the state object which has been got by you
	 * @param n the number of states you want to be returned
	 * @return
	 */
	public List<State> getNextNStates(String username, int startId, int n);
	
	/**
	 * add a new state for this user
	 * @param user current user
	 * @param content the content of the state
	 * @param pic if there is no picture, use null
	 * @return
	 */
	public State addState(User user, String content, byte[] pic);
	
	/**
	 * add a feedback for the state
	 * the returned feedback will contain the id part
	 * @param stateId the id of the state who will have this feedback
	 * @param feedbackCreator the creator of this feedback, in fact the current user will be used here
	 * @param feedback the content of the feedback
	 * @return
	 */
	public Feedback addFeedback(int stateId, User feedbackCreator, String feedback);
	
}
