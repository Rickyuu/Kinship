package com.speed.kinship.controller.impl;

import java.util.Date;
import java.util.List;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.model.Pic;
import com.speed.kinship.model.Thing;
import com.speed.kinship.model.User;

public class ThingHandlerImpl implements ThingHandler {

	@Override
	public List<Thing> getFirstNThings(String username, int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Thing> getNextNThings(String username, int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addThing(User user, String title, Date time, String content,
			Pic pic) {
		// TODO Auto-generated method stub
		return false;
	}

}
