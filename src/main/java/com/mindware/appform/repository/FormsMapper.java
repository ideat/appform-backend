package com.mindware.appform.repository;

import com.mindware.appform.entity.Forms;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FormsMapper {

    @Insert(" insert into forms " +
            "( id, id_client, " +
            " id_type_form, " +
            " reference, " +
            " description, " +
            " state, " +
            " is_final_beneficiary, " +
            " beneficiary, " +
            " services, " +
            " operations, " +
            " accounts, " +
            " debit_account, " +
            " reasons_detail, " +
            " card_number, " +
            " max_amount, " +
            " max_extension_amount, " +
            " currency, " +
            " id_card_for_verification, " +
            " creation_date," +
            " created_by, " +
            " id_user)" +
            " Values ( " +
            " #{forms.id}, " +
            " #{forms.idClient}, " +
            " #{forms.idTypeForm}, " +
            " #{forms.reference}," +
            " #{forms.description}, " +
            " #{forms.state}," +
            " #{forms.isFinalBeneficiary}," +
            " #{forms.beneficiary}," +
            " #{forms.services}," +
            " #{forms.operations}," +
            " #{forms.accounts}," +
            " #{forms.debitAccount}," +
            " #{forms.reasonsDetail}," +
            " #{forms.cardNumber}," +
            " #{forms.maxAmount}," +
            " #{forms.maxExtensionAmount}," +
            " #{forms.currency}," +
            " #{forms.idCardForVerification}," +
            " #{forms.creationDate}," +
            " #{forms.createdBy}," +
            " #{forms.idUser} )")
//    @SelectKey(statement=" SELECT LAST_INSERT_ID() ", keyProperty="id", before=false, resultType=int.class)
//    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void add(@Param("forms") Forms forms);


    @Update(" update forms set " +
            " description = #{forms.description}," +
            " state = #{forms.state}," +
            " is_final_beneficiary = #{forms.isFinalBeneficiary}," +
            " beneficiary = #{forms.beneficiary}," +
            " services = #{forms.services}," +
            " operations = #{forms.operations}," +
            " accounts = #{forms.accounts}," +
            " debit_account = #{forms.debiAccount}," +
            " reasons_detail = #{forms.reasonsDetail}," +
            " card_number = #{forms.cardNumber}," +
            " max_amount = #{forms.maxAmount}, " +
            " max_extension_amount = #{forms.maxExtensionAmount}," +
            " currency = #{forms.currency}," +
            " id_card_for_verification = #{forms.idCardForVerification}" +
            " where id = #{forms.id}" )
    Forms update(@Param("forms") Forms forms);

    @Select("select * " +
            "from forms " +
            "where id_client = #{idClient}")
    Forms findByIdCliente(@Param("idClient") Integer idClient);

    @Select(" select * " +
            " from forms " +
            " where forms.id_user = #{idUser}" +
            " order by forms.creation_date")
    List<Forms> findByIdUser(@Param("idUser") String idUser);

    @Select("select * from forms order by forms.creation_date" )
    List<Forms> findAll();
}
