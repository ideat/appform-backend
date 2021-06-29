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
            " id_rol, " +
            " image, " +
            " date_update_password, " +
            " email, " +
            " num_days_validity, " +
            " state ) values (" +
            " #{users.id}, " +
            " #{users.login}, " +
            " #{users.fullName}, " +
            " #{users.password}, " +
            " #{users.idRol}, " +
            " #{users.image}, " +
            " #{users.dateUpdatePassword}, " +
            " #{users.email}, " +
            " #{users.numDaysValidity}, " +
            " #{users.state})")
    void add(@Param("users") Users users);

    @Update("update users set " +
            " full_name = #{users.fullName}, " +
            " id_rol = #{users.idRol}, " +
            " image = #{users.image}, " +
            " email = #{users.email}, " +
            " num_days_validity = #{users.numDaysValidity}, " +
            " state = #{users.state} " +
            " where id = #{users.id}")
    void update(@Param("users") Users users);

    @Update("update users set " +
            " password= #{users.password} " +
            " where id = #{users.id}")
    void updatePassword(@Param("users") Users users);

    @Select(" select * from users order by full_name")
    List<Users> findAll();

    @Select("select * from users where login = #{login}")
    Optional<Users> findByLogin(@Param("login") String login);
}
