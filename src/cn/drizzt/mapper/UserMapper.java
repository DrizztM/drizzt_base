package cn.drizzt.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import cn.drizzt.model.User;

/**
 * 用户数据映射
 */
public interface UserMapper {

	@Select(value = "select id,name from user where name = #{name}")
	User loadUserByUsername(String name);

	@Insert(value = "insert into user (id, name) value(#{id},#{name})")
	void saveUser(User user);
}
