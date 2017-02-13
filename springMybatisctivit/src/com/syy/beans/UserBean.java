package com.syy.beans;

import java.io.Serializable;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9100007845475915923L;
	
	private Integer Id;
	private String username;
	private String password;
	private Double account;
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserBean(Integer id, String username, String password, Double account) {
		super();
		Id = id;
		this.username = username;
		this.password = password;
		this.account = account;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Double getAccount() {
		return account;
	}
	public void setAccount(Double account) {
		this.account = account;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
