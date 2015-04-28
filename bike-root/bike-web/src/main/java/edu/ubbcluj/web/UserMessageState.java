package edu.ubbcluj.web;

import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class UserMessageState extends JavaScriptComponentState {

	private static final long serialVersionUID = 1L;
	public String routeType = "none";
	public String searchType = "none";
	public String serviceName;
	public Float serviceLat;
	public Float serviceLng;
	public List<String> allNames;
	public List<Float> allLat;
	public List<Float> allLng;
	public int allSize;
	

}
