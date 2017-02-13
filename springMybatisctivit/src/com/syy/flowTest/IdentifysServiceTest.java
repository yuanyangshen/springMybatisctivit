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
		//��ȡidentityServiceʵ��
		IdentityService identityService = activitiRule.getIdentityService();
		//ͨ��identityServiceʵ���������û�����
		User user= identityService.newUser("henryyan");
		user.setFirstName("Henry");
		user.setLastName("yan");
		user.setEmail("935500688@qq.com");
		//�����û������ݿ�
		identityService.saveUser(user);
		//��֤�û��Ƿ񱣴�ɹ�
		User userInDb = identityService.createUserQuery().userId("henryyan").singleResult();
		if(userInDb != null){
			System.out.println("email = "+userInDb.getEmail());
		}else{
			System.out.println("����ʧ��");
		}
	}
}
