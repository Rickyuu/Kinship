package com.speed.kinship.net;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Arguments implements Serializable {

	private static final long serialVersionUID = -1414104556779142550L;
	private Map<String, Object> params;
	
	public Arguments() {
		params = new HashMap<String, Object>();
	}
	
	public void addArgument(String name, Object value) {
		params.put(name, value);
	}
	
	public Object getArgument(String name) {
		return params.get(name);
	}
	
}
