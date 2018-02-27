package win.panyong.dao;

import org.apache.ibatis.annotations.*;
import win.panyong.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    @Select(" SELECT * FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "account", column = "account"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "lastLoginTime", column = "last_login_time")
    })
    User getUserById(@Param("id") int id);

    @Select(" SELECT * FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "account", column = "account"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "lastLoginTime", column = "last_login_time")
    })
    Map<String, String> getUserMapById(@Param("id") int id);

    @Select(" SELECT * FROM user WHERE account = #{account}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "account", column = "account"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "lastLoginTime", column = "last_login_time")
    })
    User getUserByAccount(@Param("account") String account);

    @Update("update user set account=#{account},password=#{password},name=#{name},create_time=#{createTime},last_login_time=#{lastLoginTime} where id=#{id}")
    Integer update(User user);

    @Select(" SELECT * FROM user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "account", column = "account"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "lastLoginTime", column = "last_login_time")
    })
    List<User> getAllUser();

    @Options(useGeneratedKeys = true)
    @Insert("insert into user(account,password,name,create_time) values(#{account},#{password},#{name},#{createTime})")
    Integer createUser(User user);
}
