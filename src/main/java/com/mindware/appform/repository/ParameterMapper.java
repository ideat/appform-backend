package com.mindware.appform.repository;

import com.mindware.appform.entity.Parameter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ParameterMapper {

    @Insert("insert into parameter ( " +
            " id, " +
            " name, " +
            " category, " +
            " value ) " +
            " values ( " +
            " #{parameter.id}, " +
            " #{parameter.name}, " +
            " #{parameter.category}, " +
            " #{parameter.value} )")
    void create(@Param("parameter") Parameter parameter);

    @Update("update parameter set" +
            " name = #{parameter.name}, " +
            " category = #{parameter.category}, " +
            " value = #{parameter.value} " +
            " where id = #{parameter.id}")
    void update(@Param("parameter") Parameter parameter);

    @Select(" select * from parameter")
    List<Parameter> findAll();

    @Select(" select * from parameter " +
            " where category = #{category} " +
            " and name = #{name}")
    Optional<Parameter> findByCategoryAndName(@Param("category") String category, @Param("name") String name);

    @Select(" select * from parameter " +
            " where category = #{category} ")
    List<Parameter> findByCategory(@Param("category") String category);
}
