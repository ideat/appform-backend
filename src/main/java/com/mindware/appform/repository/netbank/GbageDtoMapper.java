package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.GbageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GbageDtoMapper {

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, camcafapt as openingDate, " +
            " 'CAJA-AHORRO' as accountName,camcancta as accountCode,camcacmon as currency " +
            " from gbage " +
            " inner join camca on gbagecage = camcacage " +
            " where gbagemrcb = 0 " +
            " and camcastat = 1 " +
            " and gbagecage = #{cage}")
    List<GbageDto> findGbageCamcaByCage(@Param("cage") Integer cage);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, pfmdpfreg as openingDate, " +
            " 'DPF' as accountName, pfmdpndep as accountCode,pfmdpcmon as currency " +
            " from gbage " +
            " inner join pfmdp on gbagecage = pfmdpcage " +
            " where gbagemrcb = 0 " +
            " and pfmdpstat = 1 " +
            " and gbagecage = #{cage}" )
    List<GbageDto> findGbagePfmdpByCage(@Param("cage") Integer cage);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, 'VARIOS' as accountName " +
            " from gbage " +
            " where gbagemrcb = 0 " +
            " and gbagecage = #{cage}" )
    List<GbageDto> findGbageByCage(@Param("cage") Integer cage);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, camcafapt as openingDate, " +
            " 'CAJA-AHORRO' as accountName,camcancta as accountCode,camcacmon as currency " +
            " from gbage " +
            " inner join camca on gbagecage = camcacage " +
            " where gbagemrcb = 0 " +
            " and camcastat = 1 " +
            " and trim(gbagendid) like #{cardNumber}")
    List<GbageDto> findGbageCamcaByCardNumber(@Param("cardNumber") String cardNumber);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, pfmdpfreg as openingDate, " +
            " 'DPF' as accountName, pfmdpndep as accountCode,pfmdpcmon as currency " +
            " from gbage " +
            " inner join pfmdp on gbagecage = pfmdpcage " +
            " where gbagemrcb = 0 " +
            " and pfmdpstat = 1 " +
            " and trim(gbagendid) like #{cardNumber}" )
    List<GbageDto> findGbagePfmdpByCardNumber(@Param("cardNumber") String cardNumber);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, 'VARIOS' as accountName " +
            " from gbage " +
            " where gbagemrcb = 0 " +
            " and trim(gbagendid) like #{cardNumber}" )
    List<GbageDto> findGbageByCardNumber(@Param("cardNumber") String cardNumber);


}
