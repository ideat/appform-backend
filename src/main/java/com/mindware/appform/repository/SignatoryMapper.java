package com.mindware.appform.repository;

import com.mindware.appform.entity.Signatory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SignatoryMapper {

    @Insert("insert into signatory (" +
            " id, " +
            " full_name, " +
            " id_card, " +
            " position, " +
            " power_notary, " +
            " date_power_notary, " +
            " number_notary, " +
            " notary_name, " +
            " plaza, " +
            " trade_registration, " +
            " active ) Values (" +
            " #{signatory.id}," +
            " #{signatory.fullName}, " +
            " #{signatory.idCard}, " +
            " #{signatory.position}," +
            " #{signatory.powerNotary}," +
            " #{signatory.datePowerNotary}," +
            " #{signatory.numberNotary}," +
            " #{signatory.notaryName}," +
            " #{signatory.plaza}, " +
            " #{signatory.tradeRegistration}, " +
            " #{signatory.active}) ")
    void add(@Param("signatory") Signatory signatory);

    @Update("update signatory set " +
            " full_name = #{signatory.fullName}," +
            " id_card = #{signatory.idCard}, " +
            " position = #{signatory.position}, " +
            " power_notary= #{signatory.powerNotary}," +
            " date_power_notary = #{signatory.datePowerNotary}," +
            " number_notary = #{signatory.numberNotary}," +
            " notary_name = #{signatory.notaryName}," +
            " plaza = #{signatory.plaza}, " +
            " trade_registration = #{signatory.tradeRegistration}, " +
            " active = #{signatory.active} ")
    void update(@Param("signatory") Signatory signatory);

    @Select("select * from signatory order by plaza, full_name")
    List<Signatory> findAll();

    @Select("select * from signatory where plaza = #{plaza} and active = 'SI'")
    Optional<Signatory> findByPlaza(@Param("plaza") Integer plaza);


}
