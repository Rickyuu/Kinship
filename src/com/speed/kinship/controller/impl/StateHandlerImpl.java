package com.speed.kinship.controller.impl;

import java.util.Date;
import java.util.List;

import com.speed.kinship.controller.StateHandler;
import com.speed.kinship.model.Feedback;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;

public class StateHandlerImpl implements StateHandler {

	@Override
	public List<State> getFirstNStates(String username, int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<State> getNextNStates(String username, int startId, int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State addState(User user, Date time, String content, byte[] pic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feedback addFeedback(int stateId, User feedbackCreator, String feedback) {
		// TODO Auto-generated method stub
		return null;
	}

}
