package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.GbageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GbageDtoMapper {

    @Select(" select camcacage as gbagecage, c.gbagenomb, c.gbagendid, g.gbagefnac, c.gbagefreg, camcafapt as openingDate, " +
            " 'CAJA-AHORRO' as accountName,cafirncta as accountCode,camcacmon as currency, cafircage as secundaryCage, " +
            " cacondesc as typeAccount" +
            " from gbage c " +
            " inner join cafir on c.gbagecage = cafircage " +
            " inner join camca on camcancta = cafirncta " +
            " inner join gbage g on g.gbagecage = camcacage " +
            " inner join cacon on caconpref = 3 and caconcorr = camcamane" +
            " where c.gbagemrcb = 0 " +
            " and camcastat = 1 " +
            " and cafirstat = 0 " +
            " and c.gbagecage = #{cage}")
    List<GbageDto> findGbageCamcaByCage(@Param("cage") Integer cage);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, pfmdpfreg as openingDate, " +
            " 'DPF' as accountName, pfmdpndep as accountCode,pfmdpcmon as currency, 0 as secundaryCage, " +
            " '' as typeAccount " +
            " from gbage " +
            " inner join pfmdp on gbagecage = pfmdpcage " +
            " where gbagemrcb = 0 " +
            " and pfmdpstat = 1 " +
            " and gbagecage = #{cage}" )
    List<GbageDto> findGbagePfmdpByCage(@Param("cage") Integer cage);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, 'VARIOS' as accountName, " +
            " gbcondesc as civilStatus, gbagedir1 as addressHome1, gbagedir2 as addressHome2 " +
            " from gbage " +
            " inner join gbcon on gbconpfij =3 and gbconcorr = gbageeciv " +
            " where gbagemrcb = 0 " +
            " and gbagecage = #{cage}" )
    List<GbageDto> findGbageByCage(@Param("cage") Integer cage);

    @Select(" select camcacage as gbagecage, c.gbagenomb, c.gbagendid, g.gbagefnac, c.gbagefreg, camcafapt as openingDate, " +
            " 'CAJA-AHORRO' as accountName,cafirncta as accountCode,camcacmon as currency, cafircage as secundaryCage ," +
            " cacondesc as typeAccount" +
            " from gbage c " +
            " inner join cafir on c.gbagecage = cafircage " +
            " inner join camca on camcancta = cafirncta " +
            " inner join gbage g on g.gbagecage = camcacage " +
            " inner join cacon on caconpref = 3 and caconcorr = camcamane" +
            " where c.gbagemrcb = 0 " +
            " and camcastat = 1 " +
            " and cafirstat = 0 " +
            " and trim(c.gbagendid) like #{cardNumber}")
    List<GbageDto> findGbageCamcaByCardNumber(@Param("cardNumber") String cardNumber);

    @Select(" select gbagecage, gbagenomb, gbagendid, gbagefnac, gbagefreg, pfmdpfreg as openingDate, " +
            " 'DPF' as accountName, pfmdpndep as accountCode,pfmdpcmon as currency, 0 as secundaryCage," +
            " '' as typeAccount " +
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
