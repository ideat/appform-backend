package com.mindware.appform.repository;

import com.mindware.appform.entity.TemplateContract;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TemplateContractMapper {

    @Insert(" insert into template_contract (" +
            " id, " +
            " file_name, " +
            " path_contract, " +
            " detail, " +
            " active, " +
            " type_saving_box, " +
            " total_participants," +
            " is_yunger," +
            " type_account," +
            " category ) " +
            " values ( " +
            " #{templateContract.id}, " +
            " #{templateContract.fileName}, " +
            " #{templateContract.pathContract}, " +
            " #{templateContract.detail}, " +
            " #{templateContract.active}, " +
            " #{templateContract.typeSavingBox}, " +
            " #{templateContract.totalParticipants}," +
            " #{templateContract.isYunger}," +
            " #{templateContract.typeAccount}," +
            " #{templateContract.category} )")
    void add(@Param("templateContract") TemplateContract templateContract);

    @Update(" update template_contract set " +
            " file_name = #{templateContract.fileName}, " +
            " path_contract = #{templateContract.pathContract}, " +
            " detail = #{templateContract.detail}, " +
            " active = #{templateContract.active}, " +
            " type_saving_box = #{templateContract.typeSavingBox}," +
            " total_participants = #{templateContract.totalParticipants}, " +
            " is_yunger = #{templateContract.isYunger}," +
            " type_account = #{templateContract.typeAccount}," +
            " category = #{templateContract.category} " +
            " where id = #{templateContract.id }")
    void update(@Param("templateContract") TemplateContract templateContract);

    @Update(" update template_contract set " +
            " active = #{templateContract.active} " +
            " where id = #{templateContract.id }")
    void updateActive(@Param("templateContract") TemplateContract templateContract);

    @Select(" select * from template_contract")
    List<TemplateContract> findAll();

    @Select(("select * from template_contract " +
            " where category = #{category}"))
    List<TemplateContract> findByCategory(@Param("category") String category);

    @Select(" select * " +
            " from template_contract " +
            " where file_name = #{fileName} ")
    Optional<TemplateContract> findByFileName(@Param("fileName") String fileName);

    @Select(" select * " +
            " from template_contract " +
            " where type_saving_box like #{typeSavingBox} ")
    Optional<TemplateContract> findByTypeSavingBox(@Param("typeSavingBox") String typeSavingBox);

}
