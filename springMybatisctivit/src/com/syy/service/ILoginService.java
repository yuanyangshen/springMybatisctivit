package com.syy.service;

import com.syy.beans.UserBean;

public interface ILoginService {
	
	public UserBean login(String username,String password);

}
