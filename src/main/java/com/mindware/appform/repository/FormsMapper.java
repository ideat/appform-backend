package com.mindware.appform.repository;

import com.mindware.appform.entity.Forms;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FormsMapper {

    @Insert(" insert into forms " +
            "( id, " +
            " id_client, " +
            " id_account, " +
            " name_type_form, " +
            " category_type_form, " +
            " product, " +
            " reference, " +
            " description, " +
            " state, " +
            " linking_account, " +
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
            " #{forms.idAccount}, " +
            " #{forms.nameTypeForm}," +
            " #{forms.categoryTypeForm}, " +
            " #{forms.product}, " +
            " #{forms.reference}," +
            " #{forms.description}, " +
            " #{forms.state}, " +
            " #{forms.linkingAccount}, " +
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
            " state = #{forms.state}, " +
            " product = #{forms.product}, " +
            " category_type_form = #{forms.categoryTypeForm}, " +
            " name_type_form = #{forms.nameTypeForm}, " +
            " linking_account = #{forms.linkingAccount}, " +
            " is_final_beneficiary = #{forms.isFinalBeneficiary}, " +
            " beneficiary = #{forms.beneficiary}," +
            " services = #{forms.services}," +
            " operations = #{forms.operations}," +
            " accounts = #{forms.accounts}," +
            " debit_account = #{forms.debitAccount}," +
            " reasons_detail = #{forms.reasonsDetail}," +
            " card_number = #{forms.cardNumber}," +
            " max_amount = #{forms.maxAmount}, " +
            " max_extension_amount = #{forms.maxExtensionAmount}," +
            " currency = #{forms.currency}," +
            " id_card_for_verification = #{forms.idCardForVerification}" +
            " where id = #{forms.id}" )
    void update(@Param("forms") Forms forms);

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

    @Select("select * from forms " +
            " where id_account = #{idAccount} " +
            " and name_type_form = #{typeForm} " +
            " and category_type_form = #{categoryTypeForm}")
    Optional<Forms> findByIdAccountAndTypeFormAndCategoryTypeForm(@Param("idAccount") String idAccount,
                                                                  @Param("typeForm") String typeForm,
                                                                  @Param("categoryTypeForm") String categoryTypeForm);
}
