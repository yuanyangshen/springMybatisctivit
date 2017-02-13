package com.syy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.syy.beans.UserBean;
import com.syy.service.ILoginService;

@Controller
public class LoginController {
	
	@Resource
	private ILoginService loginServiceImp;
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req, UserBean user){
		System.out.println("111111");
		ModelAndView mv = new ModelAndView();
		UserBean u = loginServiceImp.login(user.getUsername(), user.getPassword());
		
		if(u != null){
			req.getSession().setAttribute("user", u);
			mv.addObject("password",u.getPassword());
			System.out.println(u.getPassword());
		}
		mv.setViewName("userMessage");
		return mv;
	}
}
