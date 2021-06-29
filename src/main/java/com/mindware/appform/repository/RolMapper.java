package com.mindware.appform.repository;

import com.mindware.appform.entity.Rol;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RolMapper {

    @Insert(" insert into rol " +
            "( id," +
            " name, " +
            " description, " +
            " options, " +
            " scopes )" +
            " values (" +
            " #{rol.id}, " +
            " #{rol.name}, " +
            " #{rol.description}, " +
            " #{rol.options}," +
            " #{rol.scopes} )")
    void add(@Param("rol") Rol rol);

    @Update(" update rol set " +
            " name = #{rol.name}, " +
            " description = #{rol.description}, " +
            " options = #{rol.options}," +
            " scopes = #{rol.scopes} " +
            " where id = #{rol.id}")
    void update(@Param("rol") Rol rol);

    @Select("select * from rol order by name")
    List<Rol> findAll();

    @Select("Select * from rol where name = #{name}")
    Optional<Rol> findByName(@Param("name") String name);
}
