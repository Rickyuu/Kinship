package com.speed.kinship.controller;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.User;

public interface UserHandler {

	/**
	 * judge if the login legal or illegal
	 * if the user does not exist, will return null
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public User login(String username, String password, Identity identity); 
	
	/**
	 * judge if the register legal or illegal
	 * if the user name already be used, will return null
	 * remember each register will help you register two users, one for you, one for your parent or child
	 * the returned value will be you
	 * @param username
	 * @param password
	 * @param identity
	 * @return
	 */
	public User register(String username, String password, Identity identity);
	
}
