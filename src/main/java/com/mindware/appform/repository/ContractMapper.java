package com.mindware.appform.repository;

import com.mindware.appform.entity.Contract;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface ContractMapper {

    @Insert(" insert into contract " +
            " (id, " +
            " code_client, " +
            " type_account, " +
            " account, " +
            " date_contract, " +
            " file_name, " +
            " path_contract, " +
            " path_template, " +
            " number_power, " +
            " legal_representative, " +
            " id_card_legal_representative, " +
            " notary_number, " +
            " notary_name)" +
            " values(" +
            " #{contract.id}," +
            " #{contract.codeClient}," +
            " #{contract.typeAccount}," +
            " #{contract.account}," +
            " #{contract.dateContract}," +
            " #{contract.fileName}," +
            " #{contract.pathContract}," +
            " #{contract.pathTemplate}," +
            " #{contract.numberPower}," +
            " #{contract.legalRepresentative}," +
            " #{contract.idCardLegalRepresentative}," +
            " #{contract.notaryNumber}," +
            " #{contract.notaryName} ")
    void add(@Param("contract")Contract contract);

    @Update(" update contract set " +
            " date_contract = #{contract.dateContract}, " +
            " number_power = #{contract.numberPower}, " +
            " legal_representative = #{contract.legalRepresentative}, " +
            " id_card_legal_representative = #{contract.idCardLegalRepresentative}, " +
            " notary_number = #{contract.notaryNumber}, " +
            " notary_name = #{contract.notaryName} " +
            " where id = #{contract.id}")
    void update(@Param("contract") Contract contract);

    @Select("select * " +
            " from contract " +
            " where id = #{id}")
    Optional<Contract> findById(@Param("id") String id);

    @Select("select * " +
            " from contract " +
            " where account = #{account}")
    Optional<Contract> findByAccount(@Param("account") String account);

}
