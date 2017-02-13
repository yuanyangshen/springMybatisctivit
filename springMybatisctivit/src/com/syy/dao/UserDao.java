package com.syy.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.syy.beans.UserBean;

public interface UserDao {
	
	@Select("select * from user where username=#{un} and password=#{pw}")
	@Results({
		@Result(id=true,property="id",column="id",javaType=Integer.class),
		@Result(property="username",column="username",javaType=String.class),
		@Result(property="password",column="password",javaType=String.class),
		@Result(property="account",column="account",javaType=Double.class)
	})
	public UserBean login(@Param("un") String username,@Param("pw") String password);
}
