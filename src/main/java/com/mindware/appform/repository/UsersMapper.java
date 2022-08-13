package com.mindware.appform.repository;

import com.mindware.appform.dto.SignUpDto;
import com.mindware.appform.entity.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UsersMapper {

    @Insert("insert into users ( " +
            " id, " +
            " login, " +
            " full_name, " +
            " password, " +
            " rol_name, " +
            " image, " +
            " date_update_password, " +
            " email, " +
            " num_days_validity, " +
            " state, " +
            " create_date, " +
            " ad_user ) values (" +
            " #{users.id}, " +
            " #{users.login}, " +
            " #{users.fullName}, " +
            " #{users.password}, " +
            " #{users.rolName}, " +
            " #{users.image}, " +
            " #{users.dateUpdatePassword}, " +
            " #{users.email}, " +
            " #{users.numDaysValidity}, " +
            " #{users.state}, " +
            " #{users.createDate}, " +
            " #{users.adUser})")
    void add(@Param("users") Users users);

    @Update("update users set " +
            " full_name = #{users.fullName}, " +
            " rol_name = #{users.rolName}, " +
            " image = #{users.image}, " +
            " email = #{users.email}, " +
            " num_days_validity = #{users.numDaysValidity}, " +
            " state = #{users.state}," +
            " ad_user = #{users.adUser} " +
            " where id = #{users.id}")
    void update(@Param("users") Users users);

    @Update("update users set " +
            " password= #{users.password} ," +
            " state = #{users.state}, " +
            " date_update_password = #{users.dateUpdatePassword}" +
            " where id = #{users.id}")
    void updatePassword(@Param("users") Users users);

    @Update("update users set " +
            " password= #{users.password} ," +
            " state = 'RESET' " +
            " where id = #{users.id}")
    void resetPassword(@Param("users") Users users);

    @Select(" select * from users order by full_name")
    List<Users> findAll();

    @Select("select * from users where login = #{login}")
    Optional<Users> findByLogin(@Param("login") String login);

    @Select("select * from users where ad_user = #{adUser}")
    Optional<Users> findByAdUser(@Param("adUser") String adUser);

    @Select(" select * from users where users.rol_name = #{rolName}")
    List<Users> findByRol(@Param("rolName") String rolName);
}
