package com.homolo.homolo.dao;

import com.homolo.homolo.entity.system.Role;
import com.homolo.homolo.entity.system.User;

import java.util.List;

/**
 * @Author: zh
 * @Description: 用户接口.
 * @Date: Created on 下午1:15 19-9-26
 */
public interface UserServiceDao {
	//根据用户id加载用户
	User loadUserByUserId(String userid);
	//根据用户id加载权限列表
	List<Role> loadRoleByUserId(String userid);
	//测试插入数据
	int testI();
	//测试用存储过程批量插入数据
	void testBatchInsertProcedure(Integer num);
	//创建测试用户
	void createTestUser(User user);
}
