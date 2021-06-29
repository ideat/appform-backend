package com.mindware.appform.repository;

import com.mindware.appform.entity.TypeForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TypeFormMapper {

    @Insert("insert into type_form (" +
            " id, " +
            " category, " +
            " name, " +
            " services, " +
            " operations, " +
            " state " +
            " Values ( " +
            " #{typeForm.id}, " +
            " #{typeForm.category}, " +
            " #{typeForm.name}, " +
            " #{typeForm.services}, " +
            " #{typeForm.operations}, " +
            " #{typeForm.state} )")
    TypeForm add(@Param("typeForm") TypeForm typeForm);

    @Update("upcate type_form set " +
            " category = #{typeForm.category}, " +
            " name = #{typeForm.name}, " +
            " services = #{typeForm.services}, " +
            " operations = #{typeForm.operations}, " +
            " state = #{typeForm.state} " +
            " where id = #{typeForm.id}")
    TypeForm update(@Param("typeForm") TypeForm typeForm);

    @Select(" select * from type_form order by name")
    List<TypeForm> findAll();

}
