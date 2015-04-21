package edu.ubbcluj.backend.model;
// Generated Feb 4, 2015 1:47:58 PM by Hibernate Tools 4.3.1

/**
 * Openhours generated by hbm2java
 */
public class Openhours implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Integer day;
	private Integer open;
	private Integer close;
	private Services services;

	public Openhours() {
	}

	public Openhours(int id) {
		this.id = id;
	}

	public Openhours(int id, Services services, Integer day, Integer open,
			Integer close) {
		this.id = id;
		this.services = services;
		this.day= day;
		this.open = open;
		this.close = close;
	}
	
	public Openhours( Services services, Integer day, Integer open,
			Integer close) {
		this.services = services;
		this.day= day;
		this.open = open;
		this.close = close;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Services getServices() {
		return this.services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
	
	public Integer getDay() {
		return this.day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	public Integer getOpen() {
		return this.open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}
	
	public Integer getClose() {
		return this.close;
	}

	public void setClose(Integer close) {
		this.close = close;
	}
	
	public String toString(){
		return "Openhours[ day=" +this.day +"open: "+this.open + "close: "+ this.close+ "serviceid : "+this.services.getId()+"]";
		
	}

}