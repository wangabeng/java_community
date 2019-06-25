package life.majiang.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import life.majiang.community.model.User;

@Mapper
public interface UserMapper {
	@Insert("INSERT INTO user (name,account_id, token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
	void insert(User user);

	@Select("SELECT * FROM USER WHERE token=#{token}")
	public User findByToken(@Param("token")String token);
}
