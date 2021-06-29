package com.mindware.appform.repository;

import com.mindware.appform.entity.Signatory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignatoryMapper {

    @Insert("insert into signatory (" +
            " id, " +
            " full_name, " +
            " id_card, " +
            " extension, " +
            " position, " +
            " power_attorney, " +
            " date_power_attorney, " +
            " number_notary, " +
            " notary_name, " +
            " id_office ) Values (" +
            " #{signatory.id}," +
            " #{signatory.fullName}, " +
            " #{signatory.idCard}, " +
            " #{signatory.extension}," +
            " #{signatory.position}," +
            " #{signatory.powerAttorney}," +
            " #{signatory.datePowerAttorney}," +
            " #{signatory.numberNotary}," +
            " #{signatory.notaryName}," +
            " #{signatory.idOffice} ) ")
    Signatory add(@Param("signatory") Signatory signatory);

    @Update("update signatory set " +
            " full_name = #{signatory.fullName}," +
            " id_card = #{signatory.idCard}, " +
            " extension = #{signatory.extension}," +
            " position = #{signatory.extension}," +
            " power_attorney = #{signatory.powerAttorney}," +
            " date_power_attorney = #{signatory.datePowerAttorney}," +
            " number_notary = #{signatory.numberNotary}," +
            " notary_name = #{signatory.notaryName}," +
            " id_office = #{signatory.idOffice} ")
    Signatory update(@Param("signatory") Signatory signatory);

    @Select("select * from signatory order by id_office, full_name")
    List<Signatory> findAll();

    @Select("select * from signatory where id_office = #{idOffice}")
    List<Signatory> findByIdOffice(@Param("idOffice") Integer idOffice);


}
