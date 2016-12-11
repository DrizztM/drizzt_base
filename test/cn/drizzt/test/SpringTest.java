package cn.drizzt.test;

import org.springframework.context.ApplicationContext;

import cn.drizzt.mapper.UserMapper;
import cn.drizzt.model.User;

public class SpringTest {
	private static ApplicationContext ctx = SpringUtils.getApplicationContext();

	public static void main(String[] args) throws Exception {

		UserMapper userMapper = (UserMapper) ctx.getBean("userMapper", UserMapper.class);
		User u = userMapper.loadUserByUsername("name1");
		System.out.println(u.getId());
	}

}
