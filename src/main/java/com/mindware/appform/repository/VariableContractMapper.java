package com.mindware.appform.repository;

import com.mindware.appform.entity.VariableContract;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface VariableContractMapper {

    @Insert(" insert into variable_contract ( " +
            " id, " +
            " type_variable, " +
            " name, " +
            " variable )" +
            " values( " +
            " #{variableContract.id}, " +
            " #{variableContract.typeVariable}, " +
            " #{variableContract.name}, " +
            " #{variableContract.variable} )")
    void add(@Param("variableContract")VariableContract variableContract);

    @Update(" update variable_contract set " +
            " name = #{variableContract.name}, " +
            " type_variable = #{variableContract.typeVariable}, " +
            " variable = #{variableContract.variable} " +
            " where id = #{variableContract.id} ")
    void update(@Param("variableContract") VariableContract variableContract);

    @Select(" select * from variable_contract")
    List<VariableContract> findAll();

    @Select(" select * " +
            " from variable_contract " +
            " where name = #{name} ")
    Optional<VariableContract> findByName(@Param("name") String name);
}
