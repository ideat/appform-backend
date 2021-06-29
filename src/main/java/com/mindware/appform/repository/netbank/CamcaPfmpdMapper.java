package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.CamcaPfmdp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CamcaPfmpdMapper {

    @Select(" select gbagenomb, trim(gbagendid),camcafapt as createDate, camcancta as account, 'CAJA AHORRO' as typeAccount, cacondesc as state " +
            " from gbage " +
            " inner join camca on camcacage = gbagecage " +
            " inner join cacon on camcastat = caconcorr and caconpref = 4 " +
            " where gbagecage = #{cage} " +
            " union " +
            " select gbagenomb, trim(gbagendid),pfmdpfror as createDate, cast(pfmdpndep as char(15)) as  account, 'DPF' as typeAccount, pfcondesc as state " +
            " from gbage " +
            " inner join pfmdp on pfmdpcage = gbagecage " +
            " inner join pfcon on pfmdpstat = pfconcorr and pfconpfij = 5 " +
            " where gbagecage = #{cage} " +
            " and pfmdpstat in (1,4)")
    List<CamcaPfmdp> findCamcaPfmdpByCage(@Param("cage") Integer cage);

}
