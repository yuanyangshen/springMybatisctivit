package com.syy.flowTest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class IdentifysServiceTest {
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void testUser(){
		//获取identityService实例
		IdentityService identityService = activitiRule.getIdentityService();
		//通过identityService实力来创建用户对象
		User user= identityService.newUser("henryyan");
		user.setFirstName("Henry");
		user.setLastName("yan");
		user.setEmail("935500688@qq.com");
		//保存用户到数据库
		identityService.saveUser(user);
		//验证用户是否保存成功
		User userInDb = identityService.createUserQuery().userId("henryyan").singleResult();
		if(userInDb != null){
			System.out.println("email = "+userInDb.getEmail());
		}else{
			System.out.println("保存失败");
		}
	}
}
