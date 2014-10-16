package com.speed.kinship.controller.impl;

import java.util.Date;
import java.util.List;

import com.speed.kinship.controller.MemoryHandler;
import com.speed.kinship.model.Memory;
import com.speed.kinship.model.User;

public class MemoryHandlerImpl implements MemoryHandler {

	@Override
	public List<Memory> getFirstNMemories(String username, int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Memory> getNextNMemories(String username, int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addMemory(User user, Date time, String content) {
		// TODO Auto-generated method stub
		return false;
	}

}
