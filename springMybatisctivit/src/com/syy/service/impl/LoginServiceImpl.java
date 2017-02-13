package com.syy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.syy.beans.UserBean;
import com.syy.dao.UserDao;
import com.syy.service.ILoginService;
@Service
public class LoginServiceImpl implements ILoginService {
	
	@Resource
	private UserDao dao;

	@Override
	public UserBean login(String username, String password) {
		// TODO Auto-generated method stub
		System.out.println("2222");
		return dao.login(username, password);
	}

}
